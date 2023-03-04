package com.food.controller;

import com.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
