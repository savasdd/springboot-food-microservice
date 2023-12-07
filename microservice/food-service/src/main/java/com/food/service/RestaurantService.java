package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Food;
import com.food.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    List<Restaurant> getAll() throws GeneralException, GeneralWarning;

    Restaurant getByOne(Long id) throws GeneralException, GeneralWarning;

    LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning;

    Restaurant create(Restaurant dto) throws GeneralException, GeneralWarning;

    Restaurant update(Long id, Restaurant dto) throws GeneralException, GeneralWarning;

    Restaurant delete(Long id) throws GeneralException, GeneralWarning;

}
