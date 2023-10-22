package com.food.service;

import com.food.model.LogCategory;
import com.food.model.LogFood;

import java.util.List;

public interface CategoryService {

    List<LogCategory> getLogCategory();

    LogCategory getOneLogCategory(String id);

    LogCategory createLogCategory(LogCategory dto);

    void deleteLogCategory(String id);

}
