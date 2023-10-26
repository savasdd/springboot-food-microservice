package com.food.controller;

import com.food.exception.GeneralException;
import com.food.service.UserService;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<UserRepresentation> getGatewayUser(@PathVariable String username) throws GeneralException {
        return new ResponseEntity<>(service.getUser(username), HttpStatus.OK);
    }

    //@RolesAllowed({"ADMIN_ROLU"})
    @GetMapping("/roles")
    public ResponseEntity<List<RoleRepresentation>> getGatewayRoles() throws GeneralException {
        return new ResponseEntity<>(service.getRoles(), HttpStatus.OK);
    }

    @GetMapping("/groups")
    public ResponseEntity<List<GroupRepresentation>> getGatewayGroup() throws GeneralException {
        return new ResponseEntity<>(service.getGroup(), HttpStatus.OK);
    }
}
