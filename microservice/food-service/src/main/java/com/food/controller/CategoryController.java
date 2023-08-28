package com.food.controller;

import com.food.model.Category;
import com.food.service.CategoryService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(description = "Category getAll by loadResult")
    @PostMapping(value = "/categorys/all")
    public ResponseEntity<LoadResult<Category>> getAllParameter(@RequestBody DataSourceLoadOptions<Category> loadOptions) {
        return new ResponseEntity<>(service.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Category getAll")
    @GetMapping(value = "/categorys")
    public ResponseEntity<List<Category>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(description = "Category save")
    @PostMapping(value = "/categorys")
    public ResponseEntity<Category> create(@RequestBody Category dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Category update by id")
    @PutMapping(value = "/categorys/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") Long id, @RequestBody Category dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @Operation(description = "Category delete by id")
    @DeleteMapping(value = "/categorys/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
