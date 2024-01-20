package com.spring.tming.global.security;

import static com.spring.tming.global.jwt.JwtUtil.REFRESH_TOKEN_HEADER;

import com.spring.tming.global.jwt.JwtUtil;
import com.spring.tming.global.redis.RedisUtil;
import com.spring.tming.global.validator.TokenValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Logout_Handler_Impl")
@Component
@RequiredArgsConstructor
public class LogoutHandlerImpl implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final RedisUtil redisUtil;

    @Override
    public void logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String refreshToken = jwtUtil.getTokenWithoutBearer(request.getHeader(REFRESH_TOKEN_HEADER));

        TokenValidator.validate(jwtUtil.validateToken(refreshToken));

        if (redisUtil.hasKey(refreshToken)) {
            redisUtil.delete(refreshToken);
        }
    }
}
