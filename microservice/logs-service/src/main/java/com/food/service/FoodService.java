package com.food.service;

import com.food.model.LogFood;

import java.util.List;

public interface FoodService {

    List<LogFood> getLogFood();

    LogFood getOneLogFood(String id);

    LogFood createLogFood(LogFood dto);

    void deleteLogFood(String id);

}
