package com.food.service;

import com.food.dto.FoodDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Food;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;

public interface FoodService {

    List<FoodDto> getAll() throws GeneralException, GeneralWarning;

    Food getByOne(String id) throws GeneralException, GeneralWarning;

    LoadResult<Food> getAll(DataSourceLoadOptions<Food> loadOptions) throws GeneralException, GeneralWarning;
    LoadResult<Food> getAllOrder(DataSourceLoadOptions<Food> loadOptions) throws GeneralException, GeneralWarning;

    Food create(Food dto) throws GeneralException, GeneralWarning;

    FoodDto update(String id, Food dto) throws GeneralException, GeneralWarning;

    Food delete(String id) throws GeneralException, GeneralWarning;

}
