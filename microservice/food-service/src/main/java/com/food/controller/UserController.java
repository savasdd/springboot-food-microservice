package com.food.controller;

import com.food.dto.UserDto;
import com.food.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserController {

    private final CategoryService service;

    public UserController(CategoryService service) {
        this.service = service;
    }


    @Operation(description = "Users load image save")
    @PostMapping(value = "/users/upload", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> userLoadImage(@RequestBody UserDto dto) {
        System.out.println(dto);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }


}
