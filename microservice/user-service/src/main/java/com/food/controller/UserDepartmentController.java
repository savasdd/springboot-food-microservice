package com.food.controller;

import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.UserDepartment;
import com.food.service.UserDepartmentService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/departments/users")
public class UserDepartmentController {
    private final UserDepartmentService service;

    public UserDepartmentController(UserDepartmentService service) {
        this.service = service;
    }

    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDepartment>> getAllDepartmentUser() throws GeneralException {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(description = "UserDepartment getAll by loadResult")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult<UserDepartment>> getAllDepartmentUserLoad(@RequestBody DataSourceLoadOptions<UserDepartment> loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.getAll(loadOptions), HttpStatus.OK);
    }

    @GetMapping(value = "/getOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDepartment> getOneDepartmentUser(@PathVariable Long id) throws GeneralException {
        return ResponseEntity.ok(service.getOne(id));
    }

    @PostMapping(value = "/save")
    public ResponseEntity<UserDepartment> createDepartmentUser(@RequestBody UserDepartment dto) throws GeneralException {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<UserDepartment> updateDepartmentUser(@PathVariable Long id, @RequestBody UserDepartment dto) throws GeneralException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDepartmentUser(@PathVariable Long id) throws GeneralException {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
