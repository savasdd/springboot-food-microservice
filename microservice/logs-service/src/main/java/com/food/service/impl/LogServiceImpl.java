package com.food.service.impl;

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
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    private final FoodRepository foodRepository;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public LogServiceImpl(FoodRepository foodRepository, AccountRepository accountRepository, StockRepository stockRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.foodRepository = foodRepository;
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @KafkaListener(topics = LogUtils.FOOD_LOG, groupId = LogUtils.GROUP_ID)
    @Transactional
    public void consumeFoodLog(LogFoodEvent event) {
        if (event.getLog() != null) {
            createFood(modelMapper.map(event.getLog(), LogFood.class));
        }
        log.info(event.getMessage() + " {}", event.getStatus());
    }

    @Override
    @KafkaListener(topics = LogUtils.STOCK_LOG, groupId = LogUtils.GROUP_ID)
    @Transactional
    public void consumeStockLog(LogStockEvent event) {
        if (event.getLog() != null) {
            createStock(modelMapper.map(event.getLog(), LogStock.class));
        }
        log.info(event.getMessage() + " {}", event.getStatus());
    }


    @Override
    @Transactional
    public List<LogFood> getAllFood() {
        var list = foodRepository.findAll();
        log.info("list foods {} ", list.size());
        return list;
    }

    private Boolean createFood(LogFood dto) {
        var model = foodRepository.save(dto);
        log.info("create food {} ", model.getId());
        return true;
    }

    @Override
    @Transactional
    public List<LogAccount> getAllAccount() {
        var list = accountRepository.findAll();
        log.info("list accounts {} ", list.size());
        return list;
    }

    private Boolean createAccount(LogAccount dto) {
        var model = accountRepository.save(dto);
        log.info("create account {} ", model.getId());
        return true;
    }

    @Override
    @Transactional
    public List<LogStock> getAllStock() {
        var list = stockRepository.findAll();
        log.info("list stock {} ", list.size());
        return list;
    }

    private Boolean createStock(LogStock dto) {
        var model = stockRepository.save(dto);
        log.info("create stock {} ", model.getId());
        return true;
    }

    @Override
    @Transactional
    public List<LogCategory> getAllCategory() {
        var list = categoryRepository.findAll();
        log.info("list category {} ", list.size());
        return list;
    }


}
