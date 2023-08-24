package com.food.controller;

import com.food.dto.FoodDto;
import com.food.event.OrderEvent;
import com.food.model.Food;
import com.food.service.FoodService;
import com.food.service.impl.OrderServiceImpl;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
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

    @PostMapping(value = "/foods/all")
    public ResponseEntity<LoadResult<Food>> getAllParameter(@RequestBody DataSourceLoadOptions<Food> loadOptions) {
        return new ResponseEntity<>(foodService.getAll(loadOptions), HttpStatus.OK);
    }

    @GetMapping(value = "/foods")
    public ResponseEntity<List<FoodDto>> getAll() {
        return new ResponseEntity<>(foodService.getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/foods")
    public ResponseEntity<FoodDto> create(@RequestBody FoodDto dto) {
        return new ResponseEntity<>(foodService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> update(@PathVariable("id") UUID id, @RequestBody FoodDto dto) {
        return new ResponseEntity<>(foodService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(foodService.delete(id), HttpStatus.OK);
    }

    @PostMapping(value = "/orders")
    public ResponseEntity<OrderEvent> createOrder(@RequestBody OrderEvent dto) {
        return ResponseEntity.ok(orderService.create(dto));
    }

}
