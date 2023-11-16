package com.food.controller;

import com.food.dto.FoodFileDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.service.FoodFileService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping(value = "/api/foods")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class FoodFileController {

    private final FoodFileService service;

    public FoodFileController(FoodFileService service) {
        this.service = service;
    }

    @GetMapping(value = "/file/all/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoodFileDto>> getAllFoodFile(@PathVariable Long id) throws GeneralException, GeneralWarning {
        return ResponseEntity.ok(service.getListObjects(id));
    }

    @GetMapping(value = "/file/getOne", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputStream> getFoodFile(@RequestPart("fileName") String fileName) throws GeneralException, GeneralWarning {
        return ResponseEntity.ok(service.getObjects(fileName));
    }

    @Operation(description = "Users load image save")
    @PostMapping(value = "/file/upload", consumes = {"multipart/form-data", MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodFileDto> foodFileUpload(@RequestPart("foodId") String id, @RequestPart("fileName") String fileName, @RequestPart("fileType") String fileType, @RequestPart("file") MultipartFile file) throws GeneralException, GeneralWarning {
        return ResponseEntity.ok(service.uploadFile(FoodFileDto.builder().foodId(Long.parseLong(id)).filename(fileName).fileType(fileType).fileData(file).build()));
    }

    @Operation(description = "delete image")
    @DeleteMapping(value = "/file/delete/{fileName}")
    public ResponseEntity<String> deleteFoodFile(@PathVariable("fileName") String fileName) throws GeneralException, GeneralWarning {
        return ResponseEntity.ok(service.deleteObjects(fileName));
    }


}
