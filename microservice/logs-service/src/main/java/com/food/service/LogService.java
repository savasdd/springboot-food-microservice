package com.food.service;

import com.food.event.LogAccountEvent;
import com.food.event.LogCategoryEvent;
import com.food.event.LogFoodEvent;
import com.food.event.LogStockEvent;
import com.food.model.LogAccount;
import com.food.model.LogCategory;
import com.food.model.LogFood;
import com.food.model.LogStock;
import com.food.service.impl.LogServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LogService {

    public void consumeFoodLog(LogFoodEvent event);
    public void consumeStockLog(LogStockEvent event);

    public void consumeAccountLog(LogAccountEvent event);

    public void consumeCategoryLog(LogCategoryEvent event);

    public List<LogFood> getAllFood();
    public List<LogAccount> getAllAccount();
    public List<LogStock> getAllStock();

    public List<LogCategory> getAllCategory();
}
