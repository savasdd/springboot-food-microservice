package com.food.controller;

import com.food.dto.GenericResponse;
import com.food.dto.GroupDto;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;
import com.food.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/auths")
public class GroupController {

    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    @PostMapping(value = "/groups/all", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> getGroup(@RequestBody String loadOptions) throws GeneralException {
        return new ResponseEntity<>(service.getGroup(), HttpStatus.OK);
    }

    //@RolesAllowed({"FOOD_SEARCH"})
    @PostMapping(value = "/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto dto) throws GeneralException {
        return new ResponseEntity<>(service.createGroup(dto), HttpStatus.CREATED);
    }


    @GetMapping(value = "/groups/role/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> getGroupRol(@PathVariable String id) throws GeneralException {
        return new ResponseEntity<>(service.getGroupRol(id), HttpStatus.OK);
    }

    @PostMapping(value = "/groups/role", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RolDto> addRolGroup(@RequestBody RolDto dto) throws GeneralException {
        return new ResponseEntity<>(service.addGroupRol(dto), HttpStatus.CREATED);
    }

    @PostMapping(value = "/groups/role/leave", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RolDto> leaveGroupRol(@RequestBody RolDto dto) throws GeneralException {
        return new ResponseEntity<>(service.leaveGroupRol(dto), HttpStatus.OK);
    }

}
