package com.food.controller;

import com.food.dto.StockDto;
import com.food.service.StockService;
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
    private StockService service;

    @GetMapping(value = "/stocks/{foodId}")
    public ResponseEntity<List<StockDto>> getAll(@PathVariable("foodId") UUID foodId){
        return new ResponseEntity<>(service.getStockService().getAll(foodId), HttpStatus.OK);
    }

//    @MongoLog
    @PostMapping(value = "/stocks/{foodId}")
    public ResponseEntity<StockDto> create(@PathVariable("foodId") UUID foodId,@RequestBody StockDto dto){
        return new ResponseEntity<>(service.getStockService().create(foodId,dto),HttpStatus.CREATED);
    }

//    @MongoLog
    @PutMapping(value = "/stocks/{foodId}/{id}")
    public ResponseEntity<StockDto> update(@PathVariable("foodId") UUID foodId,@PathVariable("id")UUID id,@RequestBody StockDto dto){
        return new ResponseEntity<>(service.getStockService().update(foodId,id,dto),HttpStatus.OK);
    }

//    @MongoLog
    @DeleteMapping(value = "/stocks/{foodId}/{id}")
    public ResponseEntity<StockDto> delete(@PathVariable("foodId") UUID foodId,@PathVariable("id") UUID id){
        return new ResponseEntity<>(service.getStockService().delete(foodId,id),HttpStatus.OK);
    }

}
