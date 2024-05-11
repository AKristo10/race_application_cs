package com.trail_race.race_application.service;


import com.trail_race.race_application.config.JwtProperties;
import com.trail_race.race_application.dto.request.AuthenticationRequest;
import com.trail_race.race_application.dto.response.AuthenticationResponse;
import com.trail_race.race_application.dto.response.JwtTokenResponse;
import com.trail_race.race_application.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final JwtProperties jwtProperties;
    private final UserService userService;
    private final JwtEncoder encoder;
    private final AuthenticationManager authenticationUserManager;

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
        String name = getAuthenticationName(request, authenticationUserManager);
        UserDetails userResponse = userService.loadUserByUsername(name);
        String accessToken = generateUserToken(userResponse).getJwtToken();
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .build();
    }

    public static HttpHeaders getAuthorizationHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private JwtTokenResponse generateUserToken(UserDetails userDetails) {
        JwtClaimsSet claims = getJwtClaimsSetBuilder()
                .subject(userDetails.getUsername())
                .build();
        String token = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new JwtTokenResponse(token);
    }

    private JwtClaimsSet.Builder getJwtClaimsSetBuilder() {
        int expirySeconds = jwtProperties.getAccessTokenExpirySeconds();
        Instant now = Instant.now();
        return JwtClaimsSet.builder()
                .issuer("cs")
                .issuedAt(now)
                .expiresAt(now.plus(expirySeconds, ChronoUnit.SECONDS));
    }

    private String getAuthenticationName(AuthenticationRequest request, AuthenticationManager authenticationManager)
            throws AuthenticationException {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        } catch (AuthenticationException e) {
            if (isBadCredentials(e)) {
                throw new BadCredentialsException("Provided credentials are wrong");
            }
            throw e;
        }
        return auth.getName();
    }

    private boolean isBadCredentials(AuthenticationException e) {
        return (e instanceof AuthenticationServiceException && e.getCause() instanceof NotFoundException)
                || (e instanceof BadCredentialsException);
    }


}
