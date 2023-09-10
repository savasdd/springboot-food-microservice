package com.food.controller;

import com.food.dto.UserFileDto;
import com.food.service.UserFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserFileController {

    private final UserFileService service;

    public UserFileController(UserFileService service) {
        this.service = service;
    }

    @Operation(description = "Users load image save")
    @PostMapping(value = "/users/file/upload", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userFileUpload(@RequestBody UserFileDto dto) {
        service.userFileUpload(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
