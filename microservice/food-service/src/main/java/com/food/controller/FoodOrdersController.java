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
@RequestMapping(value = "/api/foods/orders")
public class FoodOrdersController {

    private final FoodOrdersService service;

    public FoodOrdersController(FoodOrdersService service) {
        this.service = service;
    }

    @Operation(description = "Orders loadResult")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllFoodOrdersLoad(@RequestBody DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.getAll(loadOptions), HttpStatus.OK);
    }


    @Operation(description = "Orders getOne")
    @GetMapping(value = "/getOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> getFoodOrdersByOne(@PathVariable Long id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.getByOne(id), HttpStatus.OK);
    }

    @Operation(description = "Orders save")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> createFoodOrders(@RequestBody Orders dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }


    @Operation(description = "Orders delete by id")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> deleteFoodOrders(@PathVariable("id") Long id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

}
