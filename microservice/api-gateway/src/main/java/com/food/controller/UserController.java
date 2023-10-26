package com.food.controller;

import com.food.dto.GenericResponse;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;
import com.food.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(description = "get all users")
    @GetMapping("")
    public ResponseEntity<GenericResponse> getGatewayAllUser() throws GeneralException {
        return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<GenericResponse> getGatewayRoles() throws GeneralException {
        return new ResponseEntity<>(service.getRoles(), HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<RolDto> createGatewayRoles(@RequestBody RolDto dto) throws GeneralException {
        return new ResponseEntity<>(service.createRoles(dto), HttpStatus.CREATED);
    }

    @GetMapping("/groups")
    public ResponseEntity<GenericResponse> getGatewayGroup() throws GeneralException {
        return new ResponseEntity<>(service.getGroup(), HttpStatus.OK);
    }

    //@RolesAllowed({"ADMIN_ROLU"})
    @GetMapping("/user/{username}")
    public ResponseEntity<UserRepresentation> getGatewayUser(@PathVariable String username) throws GeneralException {
        return new ResponseEntity<>(service.getUser(username), HttpStatus.OK);
    }

}
