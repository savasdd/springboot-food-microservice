package com.food.controller;

import com.food.dto.KeycloakTokenResponse;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import com.food.service.AuthService;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/getToken")
    public ResponseEntity<AccessTokenResponse> getToken(@RequestBody UserDto dto) throws GeneralException {
        return new ResponseEntity<>(authService.authenticate(dto), HttpStatus.OK);
    }

    @PostMapping("/getRefreshToken")
    public ResponseEntity<AccessTokenResponse> refreshToken(@RequestBody KeycloakTokenResponse token) throws GeneralException {
        return new ResponseEntity<>(authService.refreshToken(token.getRefreshToken()), HttpStatus.OK);
    }

    //    @RolesAllowed({"ADMIN_ROLU"})
    @GetMapping("/roles")
    public ResponseEntity<List<RoleRepresentation>> getUserRoles() throws GeneralException {
        return new ResponseEntity<>(authService.getRoles(), HttpStatus.OK);
    }


}
