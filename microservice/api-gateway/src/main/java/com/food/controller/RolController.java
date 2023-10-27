package com.food.controller;

import com.food.dto.GenericResponse;
import com.food.dto.RolDto;
import com.food.exception.GeneralException;
import com.food.service.RolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class RolController {

    private final RolService service;

    public RolController(RolService service) {
        this.service = service;
    }

    @GetMapping("/roles")
    public ResponseEntity<GenericResponse> getRoles() throws GeneralException {
        return new ResponseEntity<>(service.getRoles(), HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<RolDto> createRole(@RequestBody RolDto dto) throws GeneralException {
        return new ResponseEntity<>(service.createRoles(dto), HttpStatus.CREATED);
    }

}
