package com.food.controller;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.StockProduct;
import com.food.service.StockProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/stocks/products")
public class StockProductController {

    private final StockProductService stockService;

    public StockProductController(StockProductService stockService) {
        this.stockService = stockService;
    }

    @Operation(description = "StockProduct getAll by loadResult")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllStockProductLoad(@RequestBody DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(stockService.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "StockProduct getAll")
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StockProduct>> getAllStockProduct() throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(stockService.getAll(), HttpStatus.OK);
    }

    @Operation(description = "StockProduct getById")
    @GetMapping(value = "/getOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockProduct> getStockProductOne(@PathVariable Long id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(stockService.getById(id), HttpStatus.OK);
    }

    @Operation(description = "StockProduct save")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockProduct> createStockProduct(@RequestBody StockProduct dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(stockService.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "StockProduct update by id")
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockProduct> updateStockProduct(@PathVariable("id") Long id, @RequestBody StockProduct dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(stockService.update(id, dto), HttpStatus.OK);
    }

    @Operation(description = "StockProduct delete by id")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StockProduct> deleteStockProduct(@PathVariable("id") Long id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(stockService.delete(id), HttpStatus.OK);
    }

}
