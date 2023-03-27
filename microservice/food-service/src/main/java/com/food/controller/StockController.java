package com.food.controller;

import com.food.dto.FoodDto;
import com.food.dto.StockDto;
import com.food.event.StockEvent;
import com.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class StockController {

    @Autowired
    private FoodService service;

    @GetMapping(value = "/foods/{foodId}/stocks")
    public ResponseEntity<List<FoodDto>> getAll(@PathVariable("foodId") UUID foodId){
        return new ResponseEntity<>(service.getFoodService().getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/foods/{foodId}/stocks")
    public ResponseEntity<StockEvent> create(@PathVariable("foodId") UUID foodId, @RequestBody StockDto dto){
        return new ResponseEntity<>(service.getFoodService().producerStockCreate(foodId,dto),HttpStatus.CREATED);
    }

    @PutMapping(value = "/foods/{foodId}/stocks/{id}")
    public ResponseEntity<FoodDto> update(@PathVariable("foodId") UUID foodId, @PathVariable("id") UUID id,@RequestBody FoodDto dto){
        return new ResponseEntity<>(service.getFoodService().update(id, dto),HttpStatus.OK);
    }

    @DeleteMapping(value = "/foods/{foodId}/stocks/{id}")
    public ResponseEntity<FoodDto> delete(@PathVariable("foodId") UUID foodId, @PathVariable("id") UUID id){
        return new ResponseEntity<>(service.getFoodService().delete(id),HttpStatus.OK);
    }

}
