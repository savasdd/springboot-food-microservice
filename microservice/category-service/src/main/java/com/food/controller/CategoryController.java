package com.food.controller;

import com.food.dto.CategoryDto;
import com.food.service.CategoryService;
import com.food.utils.aop.MongoLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(value = "/categorys")
    public ResponseEntity<List<CategoryDto>> getAll(){
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @MongoLog
    @PostMapping(value = "/categorys")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto){
        return new ResponseEntity<>(categoryService.create(dto),HttpStatus.CREATED);
    }

    @MongoLog
    @PutMapping(value = "/categorys/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") UUID id, @RequestBody CategoryDto dto){
        return new ResponseEntity<>(categoryService.update(id, dto),HttpStatus.OK);
    }

    @MongoLog
    @DeleteMapping(value = "/categorys/{id}")
    public ResponseEntity<CategoryDto> delete(@PathVariable("id") UUID id){
        return new ResponseEntity<>(categoryService.delete(id),HttpStatus.OK);
    }
}
