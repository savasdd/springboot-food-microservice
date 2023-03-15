package com.food.service.impl;

import com.food.model.Account;
import com.food.model.Food;
import com.food.model.Stock;
import com.food.repository.AccountRepository;
import com.food.repository.CategoryRepository;
import com.food.repository.FoodRepository;
import com.food.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogServiceImpl {

    private final FoodRepository foodRepository;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final CategoryRepository categoryRepository;


    public List<Food> getAllFood(){
        var list=foodRepository.findAll();
        log.info("list foods {} ",list.size());
        return list;
    }

    public Boolean createFood(Food dto){
        var model=foodRepository.save(dto);
        log.info("create food {} ",model.getId());
        return true;
    }
    public List<Account> getAllAccount(){
        var list=accountRepository.findAll();
        log.info("list accounts {} ",list.size());
        return list;
    }

    public Boolean createAccount(Account dto){
        var model=accountRepository.save(dto);
        log.info("create account {} ",model.getId());
        return true;
    }


    public List<Stock> getAllStock(){
        var list=stockRepository.findAll();
        log.info("list stock {} ",list.size());
        return list;
    }

    public Boolean createStock(Stock dto){
        var model=stockRepository.save(dto);
        log.info("create stock {} ",model.getId());
        return true;
    }

}
