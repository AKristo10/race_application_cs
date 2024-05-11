package com.trail_race.race_application.controller;

import com.trail_race.race_application.dto.request.AuthenticationRequest;
import com.trail_race.race_application.dto.response.AuthenticationResponse;
import com.trail_race.race_application.service.AccessTokenService;
import com.trail_race.race_application.util.Api;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AccessTokenService accessTokenService;

    @PostMapping(Api.PUBLIC + "/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = accessTokenService.authenticateUser(authenticationRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(AccessTokenService.getAuthorizationHeader(authenticationResponse.getAccessToken()))
                .body(authenticationResponse);
    }

}
