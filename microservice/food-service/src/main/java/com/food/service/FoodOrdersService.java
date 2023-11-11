package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Orders;

public interface FoodOrdersService {

    Orders getByOne(Long id) throws GeneralException, GeneralWarning;

    LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning;

    Orders create(Orders dto) throws GeneralException, GeneralWarning;

    Orders delete(Long id) throws GeneralException, GeneralWarning;

}
