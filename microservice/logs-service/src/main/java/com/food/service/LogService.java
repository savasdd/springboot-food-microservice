package com.food.service;

import com.food.event.LogFoodEvent;
import com.food.event.LogStockEvent;
import com.food.model.LogAccount;
import com.food.model.LogCategory;
import com.food.model.LogFood;
import com.food.model.LogStock;

import java.util.List;

public interface LogService {

    void consumeFoodLog(LogFoodEvent event);

    void consumeStockLog(LogStockEvent event);

    List<LogFood> getAllFood();

    List<LogAccount> getAllAccount();

    List<LogStock> getAllStock();

    List<LogCategory> getAllCategory();
}
