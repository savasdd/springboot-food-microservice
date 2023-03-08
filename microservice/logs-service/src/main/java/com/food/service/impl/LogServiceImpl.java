package com.food.service.impl;

import com.food.repository.AccountRepository;
import com.food.repository.CategoryRepository;
import com.food.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogServiceImpl {

    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;
    private final AccountRepository accountRepository;

}
