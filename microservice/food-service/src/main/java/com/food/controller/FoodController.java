package com.food.controller;

import com.food.dto.FoodDto;
import com.food.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping(value = "/foods")
    public ResponseEntity<List<FoodDto>> getAll(){
        return new ResponseEntity<>(foodService.getAll(), HttpStatus.OK);
    }

    //@MongoLog
    @PostMapping(value = "/foods")
    public ResponseEntity<FoodDto> create(@RequestBody FoodDto dto){
        return new ResponseEntity<>(foodService.create(dto),HttpStatus.CREATED);
    }

    //@MongoLog
    @PutMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> update(@PathVariable("id") UUID id,@RequestBody FoodDto dto){
        return new ResponseEntity<>(foodService.update(id, dto),HttpStatus.OK);
    }

    //@MongoLog
    @DeleteMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> delete(@PathVariable("id") UUID id){
        return new ResponseEntity<>(foodService.delete(id),HttpStatus.OK);
    }

}
