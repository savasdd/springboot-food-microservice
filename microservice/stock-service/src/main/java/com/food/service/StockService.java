package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Stock;

import java.util.List;

public interface StockService {

    LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning;

    List<Stock> getAll() throws GeneralException, GeneralWarning;

    Stock getById(Long id) throws GeneralException, GeneralWarning;

    Stock create(Stock dto) throws GeneralException, GeneralWarning;

    Stock update(Long id, Stock dto) throws GeneralException, GeneralWarning;

    Stock delete(Long id) throws GeneralException, GeneralWarning;

    List<Stock> getStockByFoodId(Long id) throws GeneralException, GeneralWarning;
}
