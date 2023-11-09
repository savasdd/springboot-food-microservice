package com.food.controller;

import com.food.dto.TokenResponse;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import com.food.service.AuthService;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/getToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> getToken(@RequestBody UserDto dto) throws GeneralException {
        return new ResponseEntity<>(authService.authenticate(dto), HttpStatus.OK);
    }

    @PostMapping(value = "/getRefreshToken", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody TokenResponse token) throws GeneralException {
        return new ResponseEntity<>(authService.refreshToken(token.getRefreshToken()), HttpStatus.OK);
    }


}
