package com.food.controller;

import com.food.dto.AccountDto;
import com.food.service.AccountService;
import com.food.utils.aop.MongoLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(allowedHeaders = "*",origins = "*")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "/accounts")
    public ResponseEntity<List<AccountDto>> getAll(){
        return ResponseEntity.ok(accountService.getAll());
    }

    @MongoLog
    @PostMapping(value = "/accounts")
    public ResponseEntity<AccountDto> create(@RequestBody AccountDto dto){
        return new ResponseEntity<>(accountService.create(dto),HttpStatus.CREATED);
    }

    @MongoLog
    @PutMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> update(@PathVariable("id") UUID id,@RequestBody AccountDto dto){
        return ResponseEntity.ok(accountService.update(id,dto));
    }

    @MongoLog
    @DeleteMapping(value = "/accounts/{id}")
    public ResponseEntity<AccountDto> delete(@PathVariable("id") UUID id){
        return ResponseEntity.ok(accountService.delete(id));
    }
}
