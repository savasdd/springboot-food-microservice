package com.food.service;

import com.food.dto.FoodDto;
import com.food.model.Food;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;

public interface FoodService {

    List<FoodDto> getAll();

    Food getByOne(String id);

    LoadResult<Food> getAll(DataSourceLoadOptions<Food> loadOptions);

    Food create(Food dto);

    FoodDto update(String id, Food dto);

    Food delete(String id);

}
