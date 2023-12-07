package com.food.controller;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Restaurant;
import com.food.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/foods/restaurants")
public class RestaurantController {

    private final RestaurantService foodService;

    public RestaurantController(RestaurantService foodService) {
        this.foodService = foodService;
    }


    @Operation(description = "Restaurant loadResult")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllRestaurantLoad(@RequestBody DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Restaurant getAll")
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Restaurant>> getAllRestaurant() throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.getAll(), HttpStatus.OK);
    }

    @Operation(description = "Restaurant getOne")
    @GetMapping(value = "/getOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> getRestaurantByOne(@PathVariable Long id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.getByOne(id), HttpStatus.OK);
    }

    @Operation(description = "Restaurant save")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Restaurant update by id")
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("id") Long id, @RequestBody Restaurant dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.update(id, dto), HttpStatus.OK);
    }

    @Operation(description = "Restaurant delete by id")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("id") Long id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.delete(id), HttpStatus.OK);
    }


}
