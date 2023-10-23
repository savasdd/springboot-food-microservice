package com.food.service;

import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Orders;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

public interface FoodOrdersService {

    Orders getByOne(String id) throws GeneralException, GeneralWarning;

    LoadResult<Orders> getAll(DataSourceLoadOptions<Orders> loadOptions) throws GeneralException, GeneralWarning;

    Orders create(Orders dto) throws GeneralException, GeneralWarning;

    Orders delete(String id) throws GeneralException, GeneralWarning;

}
