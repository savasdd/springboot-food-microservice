package com.food.controller;

import com.food.dto.StockDto;
import com.food.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping(value = "/stocks/{foodId}")
    public ResponseEntity<List<StockDto>> getAll(@PathVariable("foodId") UUID foodId) {
        return new ResponseEntity<>(stockService.getAll(foodId), HttpStatus.OK);
    }

    @PostMapping(value = "/stocks/{foodId}")
    public ResponseEntity<StockDto> create(@PathVariable("foodId") UUID foodId, @RequestBody StockDto dto) {
        return new ResponseEntity<>(stockService.create(foodId, dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/stocks/{foodId}/{id}")
    public ResponseEntity<StockDto> update(@PathVariable("foodId") UUID foodId, @PathVariable("id") UUID id, @RequestBody StockDto dto) {
        return new ResponseEntity<>(stockService.update(foodId, id, dto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/stocks/{foodId}/{id}")
    public ResponseEntity<StockDto> delete(@PathVariable("foodId") UUID foodId, @PathVariable("id") UUID id) {
        return new ResponseEntity<>(stockService.delete(foodId, id), HttpStatus.OK);
    }

}
