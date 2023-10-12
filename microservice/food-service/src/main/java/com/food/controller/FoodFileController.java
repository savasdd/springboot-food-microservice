package com.food.controller;

import com.food.dto.FoodFileDto;
import com.food.service.FoodFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/api/foods/file")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class FoodFileController {

    private final FoodFileService service;

    public FoodFileController(FoodFileService service) {
        this.service = service;
    }

    @GetMapping(value = "/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoodFileDto>> getAllFoodFile(@PathVariable String id) {
        return ResponseEntity.ok(service.getListObjects(id));
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStream> getFoodFile(@RequestPart("fileName") String fileName) {
        return ResponseEntity.ok(service.getObjects(fileName));
    }

    @Operation(description = "Users load image save")
    @PostMapping(value = "/upload", consumes = {"multipart/form-data", MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FoodFileDto> foodFileUpload(@RequestPart("foodId") String userId, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(service.uploadFile(FoodFileDto.builder().foodId(userId).fileData(file).build()));
    }


}
