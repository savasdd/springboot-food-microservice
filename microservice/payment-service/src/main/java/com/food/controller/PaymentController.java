package com.food.controller;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Payment;
import com.food.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @Operation(description = "Payment getAll by loadResult")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllPaymentLoad(@RequestBody DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Payment getAll")
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Payment>> getAllPayment() throws GeneralException, GeneralWarning {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(description = "Payment getById")
    @GetMapping(value = "/getOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> getByIdPayment(@PathVariable Long id) throws GeneralException, GeneralWarning {
        return ResponseEntity.ok(service.getByOne(id));
    }

    @Operation(description = "Payment save")
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> createPayment(@RequestBody Payment dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Payment update by id")
    @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") Long id, @RequestBody Payment dto) throws GeneralException, GeneralWarning {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(description = "Payment delete by id")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deletePayment(@PathVariable Long id) throws GeneralException, GeneralWarning {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Payment getPaymentByFood")
    @GetMapping(value = "/custom/byStock/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Payment>> getPaymentByStock(@PathVariable("id") Long id) throws GeneralException, GeneralWarning {
        return ResponseEntity.ok(service.getPaymentByStock(id));
    }


}
