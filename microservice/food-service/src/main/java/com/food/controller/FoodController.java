package com.food.controller;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.dto.FoodDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Food;
import com.food.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/foods")
//@CrossOrigin(allowedHeaders = "*", origins = "*")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @Operation(description = "Food getAll by loadResult")
    @PostMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllFoodLoad(@RequestBody DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Food getAll")
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoodDto>> getAllFood() throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.getAll(), HttpStatus.OK);
    }

    @Operation(description = "Food getOne")
    @GetMapping(value = "/getOne/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Food> getFoodByOne(@PathVariable Long id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.getByOne(id), HttpStatus.OK);
    }

    @Operation(description = "Food save")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Food> createFood(@RequestBody Food dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Food update by id")
    @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Food> updateFood(@PathVariable("id") Long id, @RequestBody Food dto) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.update(id, dto), HttpStatus.OK);
    }

    @Operation(description = "Food delete by id")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Food> deleteFood(@PathVariable("id") Long id) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.delete(id), HttpStatus.OK);
    }

    @Operation(description = "Food getAll by loadResult")
    @PostMapping(value = "/custom/orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult> getAllFoodLoadOrder(@RequestBody DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.getAllOrder(loadOptions), HttpStatus.OK);
    }

}
