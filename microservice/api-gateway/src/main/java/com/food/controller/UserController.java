package com.food.controller;

import com.food.dto.GenericResponse;
import com.food.dto.GroupDto;
import com.food.dto.RolDto;
import com.food.dto.UserDto;
import com.food.exception.GeneralException;
import com.food.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(description = "get all users")
    @GetMapping("/users")
    public ResponseEntity<GenericResponse> getAllUser() throws GeneralException {
        return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) throws GeneralException {
        return new ResponseEntity<>(service.createUser(dto), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable String id) throws GeneralException {
        return new ResponseEntity<>(service.deleteUser(id), HttpStatus.OK);
    }

    @GetMapping("/users/group/{id}")
    public ResponseEntity<GenericResponse> getUserGroup(@PathVariable String id) throws GeneralException {
        return new ResponseEntity<>(service.getUserGroup(id), HttpStatus.OK);
    }

    @PostMapping("/users/group")
    public ResponseEntity<GroupDto> joinUserGroup(@RequestBody GroupDto dto) throws GeneralException {
        return new ResponseEntity<>(service.joinUserGroup(dto), HttpStatus.CREATED);
    }

    @PostMapping("/users/group/leave")
    public ResponseEntity<GroupDto> leaveUserGroup(@RequestBody GroupDto dto) throws GeneralException {
        return new ResponseEntity<>(service.leaveUserGroup(dto), HttpStatus.OK);
    }

    //@RolesAllowed({"ADMIN_ROLU"})
    @GetMapping("/users/{username}")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable String username) throws GeneralException {
        return new ResponseEntity<>(service.getUser(username), HttpStatus.OK);
    }


}
