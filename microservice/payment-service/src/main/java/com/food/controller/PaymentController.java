package com.food.controller;

import com.food.model.Payment;
import com.food.service.PaymentService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @Operation(description = "Payment getAll by loadResult")
    @PostMapping(value = "/payments/all")
    public ResponseEntity<LoadResult<Payment>> getAllParameter(@RequestBody DataSourceLoadOptions<Payment> loadOptions) {
        return new ResponseEntity<>(service.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Payment getAll")
    @GetMapping(value = "/payments")
    public ResponseEntity<List<Payment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(description = "Payment save")
    @PostMapping(value = "/payments")
    public ResponseEntity<Payment> create(@RequestBody Payment dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Payment update by id")
    @PutMapping(value = "/payments/{id}")
    public ResponseEntity<Payment> update(@PathVariable UUID id, @RequestBody Payment dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(description = "Payment delete by id")
    @DeleteMapping(value = "/payments/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
