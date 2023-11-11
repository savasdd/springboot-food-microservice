package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.dto.FoodDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Food;

import java.util.List;

public interface FoodService {

    List<FoodDto> getAll() throws GeneralException, GeneralWarning;

    Food getByOne(Long id) throws GeneralException, GeneralWarning;

    LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning;

    LoadResult getAllOrder(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning;

    Food create(Food dto) throws GeneralException, GeneralWarning;

    Food update(Long id, Food dto) throws GeneralException, GeneralWarning;

    Food delete(Long id) throws GeneralException, GeneralWarning;

}
