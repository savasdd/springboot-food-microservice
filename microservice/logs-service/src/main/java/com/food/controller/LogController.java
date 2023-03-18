package com.food.controller;

import com.food.model.Account;
import com.food.model.Category;
import com.food.model.Food;
import com.food.model.Stock;
import com.food.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class LogController {

    @Autowired
    private LogService service;

    @GetMapping(value = "/logs/foods")
    public ResponseEntity<List<Food>> getAllFood(){
        return new ResponseEntity<>(service.getLogService().getAllFood(), HttpStatus.OK);
    }

    @PostMapping(value = "/logs/foods")
    public ResponseEntity<Boolean> createFood(@RequestBody Food dto){
        return new ResponseEntity<>(service.getLogService().createFood(dto),HttpStatus.OK);
    }

    @GetMapping(value = "/logs/accounts")
    public ResponseEntity<List<Account>> getAllAccount(){
        return new ResponseEntity<>(service.getLogService().getAllAccount(), HttpStatus.OK);
    }

    @PostMapping(value = "/logs/accounts")
    public ResponseEntity<Boolean> createAccount(@RequestBody Account dto){
        return new ResponseEntity<>(service.getLogService().createAccount(dto),HttpStatus.OK);
    }

    @GetMapping(value = "/logs/stocks")
    public ResponseEntity<List<Stock>> getAllStock(){
        return new ResponseEntity<>(service.getLogService().getAllStock(), HttpStatus.OK);
    }

    @PostMapping(value = "/logs/stocks")
    public ResponseEntity<Boolean> createStock(@RequestBody Stock dto){
        return new ResponseEntity<>(service.getLogService().createStock(dto),HttpStatus.OK);
    }


    @GetMapping(value = "/logs/categorys")
    public ResponseEntity<List<Category>> getAllCategory(){
        return new ResponseEntity<>(service.getLogService().getAllCategory(), HttpStatus.OK);
    }

    @PostMapping(value = "/logs/categorys")
    public ResponseEntity<Boolean> createCategory(@RequestBody Category dto){
        return new ResponseEntity<>(service.getLogService().createCategory(dto),HttpStatus.OK);
    }
}
