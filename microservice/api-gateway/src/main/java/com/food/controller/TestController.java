package com.food.controller;

import com.food.exception.GeneralException;
import com.food.service.AuthService;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class TestController {

    private final AuthService authService;

    public TestController(AuthService authService) {
        this.authService = authService;
    }

//    @RolesAllowed({"ADMIN_ROLU"})
    @GetMapping("/roles")
    public ResponseEntity<List<RoleRepresentation>> getUserRoles() throws GeneralException {
        return new ResponseEntity<>(authService.getRoles(), HttpStatus.OK);
    }


}
