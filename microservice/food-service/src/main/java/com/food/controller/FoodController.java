package com.food.controller;

import com.food.dto.FoodDto;
import com.food.dto.Order;
import com.food.service.FoodService;
import com.food.service.impl.OrderServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class FoodController {

    private final FoodService foodService;
    private final OrderServiceImpl orderService;

    public FoodController(FoodService foodService, OrderServiceImpl orderService) {
        this.foodService = foodService;
        this.orderService = orderService;
    }

    @GetMapping(value = "/foods")
    public ResponseEntity<List<FoodDto>> getAll() {
        return new ResponseEntity<>(foodService.getAll(), HttpStatus.OK);
    }

    //@MongoLog
    @PostMapping(value = "/foods")
    public ResponseEntity<FoodDto> create(@RequestBody FoodDto dto) {
        return new ResponseEntity<>(foodService.create(dto), HttpStatus.CREATED);
    }

    //@MongoLog
    @PutMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> update(@PathVariable("id") UUID id, @RequestBody FoodDto dto) {
        return new ResponseEntity<>(foodService.update(id, dto), HttpStatus.OK);
    }

    //@MongoLog
    @DeleteMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(foodService.delete(id), HttpStatus.OK);
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<Order> createOrder(@RequestBody Order dto) {
        return ResponseEntity.ok(orderService.create(dto));
    }

}
