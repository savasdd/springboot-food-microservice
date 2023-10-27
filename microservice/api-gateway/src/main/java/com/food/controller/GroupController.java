package com.food.controller;

import com.food.dto.GenericResponse;
import com.food.dto.GroupDto;
import com.food.exception.GeneralException;
import com.food.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class GroupController {

    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping("/groups")
    public ResponseEntity<GenericResponse> getGatewayGroup() throws GeneralException {
        return new ResponseEntity<>(service.getGroup(), HttpStatus.OK);
    }

    //@RolesAllowed({"ADMIN_ROLU"})
    @PostMapping("/groups")
    public ResponseEntity<GroupDto> createGatewayGroup(@RequestBody GroupDto dto) throws GeneralException {
        return new ResponseEntity<>(service.createGroup(dto), HttpStatus.CREATED);
    }


}
