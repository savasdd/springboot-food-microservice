package com.food.controller;

import com.food.dto.FoodDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Food;
import com.food.service.FoodService;
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
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @Operation(description = "Food getAll by loadResult")
    @PostMapping(value = "/foods/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult<Food>> getAllFoodLoad(@RequestBody DataSourceLoadOptions<Food> loadOptions) throws GeneralException, GeneralWarning {
        return new ResponseEntity<>(foodService.getAll(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Food getAll by loadResult")
    @PostMapping(value = "/foods/allOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadResult<Food>> getAllFoodLoadOrder(@RequestBody DataSourceLoadOptions<Food> loadOptions) throws GeneralException, GeneralWarning{
        return new ResponseEntity<>(foodService.getAllOrder(loadOptions), HttpStatus.OK);
    }

    @Operation(description = "Food getAll")
    @GetMapping(value = "/foods", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FoodDto>> getAllFood() throws GeneralException, GeneralWarning{
        return new ResponseEntity<>(foodService.getAll(), HttpStatus.OK);
    }

    @Operation(description = "Food getOne")
    @GetMapping(value = "/foods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Food> getFoodByOne(@PathVariable String id) throws GeneralException, GeneralWarning{
        return new ResponseEntity<>(foodService.getByOne(id), HttpStatus.OK);
    }

    @Operation(description = "Food save")
    @PostMapping(value = "/foods", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Food> createFood(@RequestBody Food dto) throws GeneralException, GeneralWarning{
        return new ResponseEntity<>(foodService.create(dto), HttpStatus.CREATED);
    }

    @Operation(description = "Food update by id")
    @PutMapping(value = "/foods/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodDto> updateFood(@PathVariable("id") String id, @RequestBody Food dto) throws GeneralException, GeneralWarning{
        return new ResponseEntity<>(foodService.update(id, dto), HttpStatus.OK);
    }

    @Operation(description = "Food delete by id")
    @DeleteMapping(value = "/foods/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Food> deleteFood(@PathVariable("id") String id) throws GeneralException, GeneralWarning{
        return new ResponseEntity<>(foodService.delete(id), HttpStatus.OK);
    }

}
