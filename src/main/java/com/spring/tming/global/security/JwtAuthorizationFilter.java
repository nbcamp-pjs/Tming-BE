package com.spring.tming.global.security;

import static com.spring.tming.global.jwt.JwtUtil.*;

import com.spring.tming.domain.user.entity.User;
import com.spring.tming.domain.user.repository.UserRepository;
import com.spring.tming.global.jwt.JwtUtil;
import com.spring.tming.global.redis.RedisUtil;
import com.spring.tming.global.validator.UserValidator;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = jwtUtil.getTokenFromHeader(request, ACCESS_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken) && !jwtUtil.validateAccessToken(accessToken)) {
            String refreshToken = jwtUtil.getTokenFromHeader(request, REFRESH_TOKEN_HEADER);

            if (StringUtils.hasText(refreshToken)
                    && jwtUtil.validateRefreshToken(refreshToken)
                    && redisUtil.hasKey(refreshToken)) {
                Long userId = Long.parseLong(redisUtil.get(refreshToken).toString());
                User user = userRepository.findByUserId(userId);
                UserValidator.validate(user);
                accessToken = jwtUtil.createAccessToken(user.getEmail()).split(" ")[1].trim();
                response.addHeader(ACCESS_TOKEN_HEADER, BEARER_PREFIX + accessToken);
            }
        }

        if (StringUtils.hasText(accessToken)) {
            Claims info = jwtUtil.getUserInfoFromToken(accessToken);
            try {
                setAuthentication(info.getSubject());
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private Authentication createAuthentication(String email) {
        User user = userRepository.findByEmail(email);
        UserValidator.validate(user);
        UserDetails userDetails = new UserDetailsImpl(user);
        return new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
