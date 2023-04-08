package com.food.service.impl;

import com.food.event.LogAccountEvent;
import com.food.event.LogCategoryEvent;
import com.food.event.LogFoodEvent;
import com.food.event.LogStockEvent;
import com.food.model.LogAccount;
import com.food.model.LogCategory;
import com.food.model.LogFood;
import com.food.model.LogStock;
import com.food.repository.AccountRepository;
import com.food.repository.CategoryRepository;
import com.food.repository.FoodRepository;
import com.food.repository.StockRepository;
import com.food.service.LogService;
import com.food.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    private final FoodRepository foodRepository;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final CategoryRepository categoryRepository;

    public LogServiceImpl(FoodRepository foodRepository, AccountRepository accountRepository, StockRepository stockRepository, CategoryRepository categoryRepository) {
        this.foodRepository = foodRepository;
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @KafkaListener(topics = LogUtils.FOOD_LOG, groupId = LogUtils.GROUP_ID)
    public void consumeFoodLog(LogFoodEvent event) {
        if(event.getLog()!=null){
            createFood(event.getLog());
        }
        log.info(event.getMessage()+" {}",event.getStatus());
    }

    @Override
    @KafkaListener(topics = LogUtils.STOCK_LOG, groupId = LogUtils.GROUP_ID)
    public void consumeStockLog(LogStockEvent event) {
        if(event.getLog()!=null){
            createStock(event.getLog());
        }
        log.info(event.getMessage()+" {}",event.getStatus());
    }

    @Override
    @KafkaListener(topics = LogUtils.ACCOUNT_LOG, groupId = LogUtils.GROUP_ID)
    public void consumeAccountLog(LogAccountEvent event) {
        if(event.getLog()!=null){
            createAccount(event.getLog());
        }
        log.info(event.getMessage()+" {}",event.getStatus());
    }

    @Override
    @KafkaListener(topics = LogUtils.CATEGORY_LOG, groupId = LogUtils.GROUP_ID)
    public void consumeCategoryLog(LogCategoryEvent event) {
        if(event.getLog()!=null){
            createCategory(event.getLog());
        }
        log.info(event.getMessage()+" {}",event.getStatus());
    }

    @Override
    public List<LogFood> getAllFood(){
        var list=foodRepository.findAll();
        log.info("list foods {} ",list.size());
        return list;
    }

    private Boolean createFood(LogFood dto){
        var model=foodRepository.save(dto);
        log.info("create food {} ",model.getId());
        return true;
    }

    @Override
    public List<LogAccount> getAllAccount(){
        var list=accountRepository.findAll();
        log.info("list accounts {} ",list.size());
        return list;
    }

    private Boolean createAccount(LogAccount dto){
        var model=accountRepository.save(dto);
        log.info("create account {} ",model.getId());
        return true;
    }

    @Override
    public List<LogStock> getAllStock(){
        var list=stockRepository.findAll();
        log.info("list stock {} ",list.size());
        return list;
    }

    private Boolean createStock(LogStock dto){
        var model=stockRepository.save(dto);
        log.info("create stock {} ",model.getId());
        return true;
    }
    @Override
    public List<LogCategory> getAllCategory(){
        var list=categoryRepository.findAll();
        log.info("list category {} ",list.size());
        return list;
    }

    private Boolean createCategory(LogCategory dto){
        var model=categoryRepository.save(dto);
        log.info("create category {} ",model.getId());
        return true;
    }

}
