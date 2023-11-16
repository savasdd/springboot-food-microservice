package com.food.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.event.OrderEvent;
import com.food.service.impl.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/foods")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class PaymentController {

    private final PaymentServiceImpl orderService;

    public PaymentController(PaymentServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Operation(description = "Orders")
    @PostMapping(value = "/payments")
    public ResponseEntity<OrderEvent> createOrderFood(@RequestBody OrderEvent dto) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.create(dto));
    }

}
