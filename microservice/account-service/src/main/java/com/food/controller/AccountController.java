package com.food.controller;

import com.food.service.AccountService;
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
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping(value = "/accounts/send")
    public ResponseEntity<String> getTest(){
        service.getAccountService().sendFood();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
