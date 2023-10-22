package com.food.service;

import com.food.model.Orders;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

public interface FoodOrdersService {

    Orders getByOne(String id);
    LoadResult<Orders> getAll(DataSourceLoadOptions<Orders> loadOptions);
    Orders create(Orders dto);
    Orders delete(String id);

}
