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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auths")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(description = "get all users")
    @PostMapping(value = "/users/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> getAllUser(@RequestBody String loadOptions) throws GeneralException {
        return new ResponseEntity<>(service.getAllUser(), HttpStatus.OK);
    }

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) throws GeneralException {
        return new ResponseEntity<>(service.createUser(dto), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> deleteUser(@PathVariable String id) throws GeneralException {
        return new ResponseEntity<>(service.deleteUser(id), HttpStatus.OK);
    }

    @GetMapping(value = "/users/group/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> getUserGroup(@PathVariable String id) throws GeneralException {
        return new ResponseEntity<>(service.getUserGroup(id), HttpStatus.OK);
    }

    @PostMapping(value = "/users/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDto> joinUserGroup(@RequestBody GroupDto dto) throws GeneralException {
        return new ResponseEntity<>(service.joinUserGroup(dto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/users/group/leave", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDto> leaveUserGroup(@RequestBody GroupDto dto) throws GeneralException {
        return new ResponseEntity<>(service.leaveUserGroup(dto), HttpStatus.OK);
    }

    //@RolesAllowed({"ADMIN_ROLU"})
    @GetMapping("/users/{username}")
    public ResponseEntity<UserRepresentation> getUser(@PathVariable String username) throws GeneralException {
        return new ResponseEntity<>(service.getUser(username), HttpStatus.OK);
    }


}
