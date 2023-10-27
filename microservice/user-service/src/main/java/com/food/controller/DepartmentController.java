package com.food.controller;

import com.food.exception.GeneralException;
import com.food.model.Department;
import com.food.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class DepartmentController {
    private final DepartmentService service;

    public DepartmentController(DepartmentService service) {
        this.service = service;
    }

    @GetMapping(value = "/departments")
    public ResponseEntity<List<Department>> getAllDepartment() throws GeneralException {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/departments/{id}")
    public ResponseEntity<Department> getOneDepartment(@PathVariable Long id) throws GeneralException {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping(value = "/departments")
    public ResponseEntity<Department> createDepartment(@RequestBody Department dto) throws GeneralException {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/departments/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department dto) throws GeneralException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping(value = "/departments/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) throws GeneralException {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
