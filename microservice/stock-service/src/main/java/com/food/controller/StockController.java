package com.food.controller;

import com.food.dto.StockDto;
import com.food.model.Stock;
import com.food.service.StockService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @Operation(description = "Stock getAll by loadResult")
    @PostMapping(value = "/stocks/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult<Stock>> getAllStockLoad(@RequestBody DataSourceLoadOptions<Stock> loadOptions) {
        return new ResponseEntity<>(stockService.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Stock getAll")
    @GetMapping(value = "/stocks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StockDto>> getAllStock() {
        return new ResponseEntity<>(stockService.getAll(), HttpStatus.OK);
    }

    @Operation(description = "Stock getById")
    @GetMapping(value = "/stocks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDto> getStockOne(@PathVariable String id) {
        return new ResponseEntity<>(stockService.getById(id), HttpStatus.OK);
    }

    @Operation(description = "Stock save")
    @PostMapping(value = "/stocks", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDto> createStock(@RequestBody StockDto dto) {
        return new ResponseEntity<>(stockService.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Stock update by id")
    @PutMapping(value = "/stocks/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDto> updateStock(@PathVariable("id") String id, @RequestBody StockDto dto) {
        return new ResponseEntity<>(stockService.update(id, dto), HttpStatus.OK);
    }

    @Operation(description = "Stock delete by id")
    @DeleteMapping(value = "/stocks/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockDto> deleteStock(@PathVariable("id") String id) {
        return new ResponseEntity<>(stockService.delete(id), HttpStatus.OK);
    }

}
