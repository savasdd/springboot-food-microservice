package com.food.controller;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.model.Category;
import com.food.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/foods/categorys")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(description = "Category loadResult")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllCategoryLoad(@RequestBody DataSourceLoadOptions loadOptions) {
        return new ResponseEntity<>(service.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Category getAll")
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> getAllCategory() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @Operation(description = "Category getOne")
    @GetMapping(value = "/getOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getCategoryByOne(@PathVariable Long id) {
        return new ResponseEntity<>(service.getByOne(id), HttpStatus.OK);
    }

    @Operation(description = "Category save")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> createCategory(@RequestBody Category dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Category update by id")
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id, @RequestBody Category dto) {
        return new ResponseEntity<>(service.update(id, dto), HttpStatus.OK);
    }

    @Operation(description = "Category delete by id")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }


}
