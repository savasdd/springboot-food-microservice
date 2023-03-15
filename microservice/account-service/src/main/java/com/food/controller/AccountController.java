package com.food.controller;

import com.food.dto.AccountDto;
import com.food.service.AccountService;
import com.food.utils.aop.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAll(){
        return new ResponseEntity<>(service.getAccountService().getAll(), HttpStatus.OK);
    }

    @Log
    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto dto){
        return new ResponseEntity<>(service.getAccountService().create(dto),HttpStatus.CREATED);
    }

    @Log
    @PutMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable("id") UUID id,@RequestBody AccountDto dto){
        return new ResponseEntity<>(service.getAccountService().update(id,dto),HttpStatus.OK);
    }

    @Log
    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> delete(@PathVariable("id") UUID id){
        return new ResponseEntity<>(service.getAccountService().delete(id),HttpStatus.OK);
    }
}
