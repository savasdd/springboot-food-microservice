package com.food.controller;

import com.food.dto.CategoryDto;
import com.food.service.CategoryService;
import com.food.utils.aop.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class CategoryController {

    @Autowired
    private CategoryService service;


    @GetMapping(value = "/categorys")
    public ResponseEntity<List<CategoryDto>> getAll(){
        return new ResponseEntity<>(service.getCategoryService().getAll(), HttpStatus.OK);
    }

    @Log
    @PostMapping(value = "/categorys")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto){
        return new ResponseEntity<>(service.getCategoryService().create(dto),HttpStatus.CREATED);
    }

    @Log
    @PutMapping(value = "/categorys/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable("id") UUID id, @RequestBody CategoryDto dto){
        return new ResponseEntity<>(service.getCategoryService().update(id, dto),HttpStatus.OK);
    }

    @Log
    @DeleteMapping(value = "/categorys/{id}")
    public ResponseEntity<CategoryDto> delete(@PathVariable("id") UUID id){
        return new ResponseEntity<>(service.getCategoryService().delete(id),HttpStatus.OK);
    }
}
