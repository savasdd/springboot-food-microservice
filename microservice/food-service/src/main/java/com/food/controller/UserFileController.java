package com.food.controller;

import com.food.dto.UserFileDto;
import com.food.service.UserFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class UserFileController {

    private final UserFileService service;

    public UserFileController(UserFileService service) {
        this.service = service;
    }

    @Operation(description = "Users load image save")
    @PostMapping(value = "/users/file/upload", consumes = {"multipart/form-data", MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserFileDto> userFileUpload(@RequestPart("userId") String userId, @RequestPart("file") MultipartFile file) {

        return ResponseEntity.ok(service.uploadFile(UserFileDto.builder().userId(userId).fileData(file).build()));
    }


}
