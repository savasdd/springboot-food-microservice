package com.food.controller;

import com.food.dto.FoodDto;
import com.food.service.FoodService;
import com.food.utils.aop.MongoLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class FoodController {

    @Autowired
    private FoodService service;

    @GetMapping(value = "/foods/send")
    public ResponseEntity<String> test(){
        service.getFoodService().sendAccount();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }


    @GetMapping(value = "/foods")
    public ResponseEntity<List<FoodDto>> getAll(){
        return new ResponseEntity<>(service.getFoodService().getAll(), HttpStatus.OK);
    }

    @MongoLog
    @PostMapping(value = "/foods")
    public ResponseEntity<FoodDto> create(@RequestBody FoodDto dto){
        return new ResponseEntity<>(service.getFoodService().create(dto),HttpStatus.CREATED);
    }

    @MongoLog
    @PutMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> update(@PathVariable("id") UUID id,@RequestBody FoodDto dto){
        return new ResponseEntity<>(service.getFoodService().update(id, dto),HttpStatus.OK);
    }

    @MongoLog
    @DeleteMapping(value = "/foods/{id}")
    public ResponseEntity<FoodDto> delete(@PathVariable("id") UUID id){
        return new ResponseEntity<>(service.getFoodService().delete(id),HttpStatus.OK);
    }

}
