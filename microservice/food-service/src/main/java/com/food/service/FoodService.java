package com.food.service;

import com.food.dto.FoodDto;
import com.food.model.Food;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;
import java.util.UUID;

public interface FoodService {

    List<FoodDto> getAll();

    FoodDto getByOne(String id);

    LoadResult<Food> getAll(DataSourceLoadOptions<Food> loadOptions);

    FoodDto create(FoodDto dto);

    FoodDto update(UUID id, FoodDto dto);

    FoodDto delete(UUID id);

}
