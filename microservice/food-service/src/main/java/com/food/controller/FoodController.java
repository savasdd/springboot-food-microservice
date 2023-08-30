package com.food.controller;

import com.food.dto.FoodDto;
import com.food.event.OrderEvent;
import com.food.model.Food;
import com.food.service.FoodService;
import com.food.service.impl.OrderServiceImpl;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class FoodController {

    private final FoodService foodService;
    private final OrderServiceImpl orderService;

    public FoodController(FoodService foodService, OrderServiceImpl orderService) {
        this.foodService = foodService;
        this.orderService = orderService;
    }

    @Operation(description = "Food getAll by loadResult")
    @PostMapping(value = "/foods/all")
    public ResponseEntity<LoadResult<Food>> getAllFoodLoad(@RequestBody DataSourceLoadOptions<Food> loadOptions) {
        return new ResponseEntity<>(foodService.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Food getAll")
    @GetMapping(value = "/foods")
    public ResponseEntity<List<FoodDto>> getAllFood() {
        return new ResponseEntity<>(foodService.getAll(), HttpStatus.OK);
    }

    @Operation(description = "Food getOne")
    @GetMapping(value = "/foods/{id}")
    public ResponseEntity<Food> getFoodByOne(@PathVariable String id) {
        return new ResponseEntity<>(foodService.getByOne(id), HttpStatus.OK);
    }

    @Operation(description = "Food save")
    @PostMapping(value = "/foods")
    public ResponseEntity<Food> createFood(@RequestBody Food dto) {
        return new ResponseEntity<>(foodService.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Food update by id")
    @PutMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> updateFood(@PathVariable("id") String id, @RequestBody Food dto) {
        return new ResponseEntity<>(foodService.update(id, dto), HttpStatus.OK);
    }

    @Operation(description = "Food delete by id")
    @DeleteMapping(value = "/foods/{id}")
    public ResponseEntity<Food> deleteFood(@PathVariable("id") String id) {
        return new ResponseEntity<>(foodService.delete(id), HttpStatus.OK);
    }

    @Operation(description = "Orders")
    @PostMapping(value = "/orders")
    public ResponseEntity<OrderEvent> createOrderFood(@RequestBody OrderEvent dto) {
        return ResponseEntity.ok(orderService.create(dto));
    }

}
