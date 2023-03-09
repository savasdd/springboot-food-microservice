package com.food.controller;

import com.food.dto.UserDto;
import com.food.dto.UserRolDto;
import com.food.service.AuthService;
import com.food.utils.kyce.KeycloakTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("/auth")
    public ResponseEntity<KeycloakTokenResponse> getToken(@RequestBody UserDto dto) throws Exception {
        return new ResponseEntity<>(service.getAuthService().getToken(dto), HttpStatus.OK);
    }

    @GetMapping("/auth/roles")
    public ResponseEntity<List<UserRolDto>> getUserRoles() throws Exception {
        return new ResponseEntity<>(service.getAuthService().getUserRoles(), HttpStatus.OK);
    }

    @PostMapping("/resfresh")
    public ResponseEntity<KeycloakTokenResponse> refreshToken(@RequestBody   KeycloakTokenResponse token) {
        return new ResponseEntity<>(service.getAuthService().refreshToken(token.getRefreshToken()), HttpStatus.OK);
    }

}
