package com.food.controller;

import com.food.dto.StockDto;
import com.food.model.Stock;
import com.food.service.StockService;
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
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping(value = "/stocks/all")
    public ResponseEntity<LoadResult<Stock>> getAllParameter(@RequestBody DataSourceLoadOptions<Stock> loadOptions) {
        return new ResponseEntity<>(stockService.getAll(loadOptions), HttpStatus.OK);
    }

    @GetMapping(value = "/stocks")
    public ResponseEntity<List<StockDto>> getAll() {
        return new ResponseEntity<>(stockService.getAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/stocks")
    public ResponseEntity<StockDto> create(@RequestBody StockDto dto) {
        return new ResponseEntity<>(stockService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping(value = "/stocks/{id}")
    public ResponseEntity<StockDto> update(@PathVariable("id") UUID id, @RequestBody StockDto dto) {
        return new ResponseEntity<>(stockService.update(id, dto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/stocks/{id}")
    public ResponseEntity<StockDto> delete(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(stockService.delete(id), HttpStatus.OK);
    }

}
