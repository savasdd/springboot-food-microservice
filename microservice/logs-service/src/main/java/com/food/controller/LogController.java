package com.food.controller;

import com.food.model.LogAccount;
import com.food.model.LogCategory;
import com.food.model.LogFood;
import com.food.model.LogStock;
import com.food.service.LogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping(value = "/logs/foods")
    public ResponseEntity<List<LogFood>> getAllFood(){
        return new ResponseEntity<>(logService.getAllFood(), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/accounts")
    public ResponseEntity<List<LogAccount>> getAllAccount(){
        return new ResponseEntity<>(logService.getAllAccount(), HttpStatus.OK);
    }

    @GetMapping(value = "/logs/stocks")
    public ResponseEntity<List<LogStock>> getAllStock(){
        return new ResponseEntity<>(logService.getAllStock(), HttpStatus.OK);
    }


    @GetMapping(value = "/logs/categorys")
    public ResponseEntity<List<LogCategory>> getAllCategory(){
        return new ResponseEntity<>(logService.getAllCategory(), HttpStatus.OK);
    }

}
