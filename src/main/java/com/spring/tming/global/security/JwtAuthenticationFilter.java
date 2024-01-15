package com.spring.tming.global.security;

import static com.spring.tming.global.meta.ResultCode.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tming.domain.user.dto.request.LoginReq;
import com.spring.tming.domain.user.dto.response.LoginRes;
import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.service.UserServiceMapper;
import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.jwt.JwtUtil;
import com.spring.tming.global.redis.RedisUtil;
import com.spring.tming.global.response.RestResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtil jwtUtil, RedisUtil redisUtil) {
        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
        setFilterProcessesUrl("/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginReq loginReq = new ObjectMapper().readValue(request.getInputStream(), LoginReq.class);
            return getAuthenticationManager()
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    loginReq.getEmail(), loginReq.getPassword(), null));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new GlobalException(NOT_FOUND_USER);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication)
            throws IOException {

        LoginRes loginRes = addTokensInHeader(authentication, response);
        settingResponse(response, RestResponse.success(loginRes));
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        if (exception instanceof BadCredentialsException
                || exception instanceof InternalAuthenticationServiceException) {
            log.error("입력정보 틀림");
            throw new GlobalException(PASSWORD_MISMATCH);
        } else {
            log.error("서버 에러");
            throw new GlobalException(SYSTEM_ERROR);
        }
    }

    private LoginRes addTokensInHeader(Authentication authentication, HttpServletResponse response) {

        User user = ((UserDetailsImpl) authentication.getPrincipal()).getUser();

        String accessToken = jwtUtil.createAccessToken(user.getUsername());
        String refreshToken = jwtUtil.createRefreshToken();

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);

        refreshToken = refreshToken.split(" ")[1].trim();
        redisUtil.set(refreshToken, user.getUserId(), JwtUtil.REFRESH_TOKEN_TIME);

        return UserServiceMapper.INSTANCE.toLoginRes(user);
    }

    private void settingResponse(HttpServletResponse response, RestResponse<?> res)
            throws IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String result = objectMapper.writeValueAsString(res);
        response.getWriter().write(result);
    }
}
