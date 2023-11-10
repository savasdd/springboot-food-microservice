package com.food.controller;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Orders;
import com.food.service.FoodOrdersService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class FoodOrdersController {

    private final FoodOrdersService service;

    public FoodOrdersController(FoodOrdersService service) {
        this.service = service;
    }

    @Operation(description = "Orders getAll by loadResult")
    @PostMapping(value = "/orders/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllFoodOrdersLoad(@RequestBody DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.getAll(loadOptions), HttpStatus.OK);
    }


    @Operation(description = "Orders getOne")
    @GetMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> getFoodOrdersByOne(@PathVariable String id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.getByOne(id), HttpStatus.OK);
    }

    @Operation(description = "Orders save")
    @PostMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> createFoodOrders(@RequestBody Orders dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }


    @Operation(description = "Orders delete by id")
    @DeleteMapping(value = "/orders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> deleteFoodOrders(@PathVariable("id") String id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

}
