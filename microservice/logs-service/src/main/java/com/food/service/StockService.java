package com.food.service;

import com.food.model.LogFood;
import com.food.model.LogStock;

import java.util.List;

public interface StockService {

    List<LogStock> getLogStock();

    LogStock getOneLogStock(String id);

    LogStock createLogStock(LogStock dto);

    void deleteLogStock(String id);

}
