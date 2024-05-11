package com.trail_race.race_application.config;

import com.trail_race.race_application.exception.UnauthorizedException;
import com.trail_race.race_application.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;

@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = JwtUtil.extractJwtFromHeader(request);
        try {
            validateToken(jwt);
        } catch (UnauthorizedException | BadJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void validateToken(String jwtToken) {
        Jwt jwt = jwtDecoder.decode(jwtToken);
        Instant expiration = jwt.getExpiresAt();
        Instant now = Instant.now();
        if (expiration != null && expiration.isBefore(now)) {
            throw new UnauthorizedException("user is not authoritzed");
        }
    }
}
