package com.food.service;

import com.food.service.impl.AccountServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountServiceImpl accountService;
}
