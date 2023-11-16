package com.food.controller;

import com.food.model.LogFood;
import com.food.service.FoodService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(allowedHeaders = "*", origins = "*")
public class LogController {

    private final FoodService logService;

    public LogController(FoodService logService) {
        this.logService = logService;
    }


    @GetMapping(value = "/foods")
    public ResponseEntity<List<LogFood>> getAllFood() {
        return new ResponseEntity<>(logService.getLogFood(), HttpStatus.OK);
    }


}
