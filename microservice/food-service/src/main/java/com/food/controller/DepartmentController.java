package com.food.controller;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Department;
import com.food.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/foods/departments")
public class DepartmentController {
    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Department>> getAllDepartment() throws GeneralException {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(description = "Department getAll by loadResult")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllDepartmentLoad(@RequestBody DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.getAll(loadOptions), HttpStatus.OK);
    }

    @GetMapping(value = "/getOne/{id}")
    public ResponseEntity<Department> getOneDepartment(@PathVariable Long id) throws GeneralException {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Department> createDepartment(@RequestBody Department dto) throws GeneralException {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department dto) throws GeneralException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) throws GeneralException {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
