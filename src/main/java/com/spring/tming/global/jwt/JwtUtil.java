package com.spring.tming.global.jwt;

import com.spring.tming.global.exception.GlobalException;
import com.spring.tming.global.meta.ResultCode;
import com.spring.tming.global.validator.TokenValidator;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
public class JwtUtil {

    public static final String ACCESS_TOKEN_HEADER = "AccessToken";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String AUTHORIZATION_KEY = "auth";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final long ACCESS_TOKEN_TIME = 10 * 60 * 1000L;
    public static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretkey;

    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(String email) {
        Date date = new Date();

        return BEARER_PREFIX
                + Jwts.builder()
                        .setSubject(email)
                        .claim(AUTHORIZATION_KEY, email)
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createRefreshToken() {
        Date date = new Date();

        return BEARER_PREFIX
                + Jwts.builder()
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String getTokenFromHeader(HttpServletRequest request, String tokenType) {
        String token = request.getHeader(tokenType);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.split(" ")[1].trim();
        }
        return null;
    }

    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new GlobalException(ResultCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
            throw new GlobalException(ResultCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new GlobalException(ResultCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new GlobalException(ResultCode.EMPTY_TOKEN);
        }
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getTokenWithoutBearer(String token) {
        TokenValidator.validate(token);
        return token.split(" ")[1].trim();
    }
}
