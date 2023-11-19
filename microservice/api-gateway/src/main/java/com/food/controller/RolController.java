package com.food.controller;

import com.food.dto.GenericResponse;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;
import com.food.service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auths")
public class RolController {

    private final RolService service;

    public RolController(RolService service) {
        this.service = service;
    }

    @PostMapping(value = "/roles/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> getRoles(@RequestBody String loadOptions) throws GeneralException {
        return new ResponseEntity<>(service.getRoles(), HttpStatus.OK);
    }

    @PostMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RolDto> createRole(@RequestBody RolDto dto) throws GeneralException {
        return new ResponseEntity<>(service.createRoles(dto), HttpStatus.CREATED);
    }

}
