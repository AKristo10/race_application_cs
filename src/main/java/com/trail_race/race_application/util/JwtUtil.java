package com.trail_race.race_application.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;

import java.time.Instant;
import java.util.Optional;

@UtilityClass
public class JwtUtil {

    static final String BEARER_TOKEN_PREFIX = "Bearer ";
    static final String AUTHORIZATION_HEADER = "Authorization";
    public String extractJwtFromHeader(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER))
                .filter(authorization -> authorization.startsWith(BEARER_TOKEN_PREFIX))
                .map(authorization -> authorization.substring(BEARER_TOKEN_PREFIX.length()))
                .orElse(null);
    }
    public static boolean isTokenExpired(String token, JwtDecoder jwtDecoder) {
        Jwt jwt = jwtDecoder.decode(token);
        Instant expiration = jwt.getExpiresAt();
        Instant now = Instant.now();
        return expiration != null && expiration.isBefore(now);
    }
}
