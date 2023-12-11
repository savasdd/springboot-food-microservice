package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.StockProduct;

import java.util.List;

public interface StockProductService {

    LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning;

    List<StockProduct> getAll() throws GeneralException, GeneralWarning;

    StockProduct getById(Long id) throws GeneralException, GeneralWarning;

    StockProduct create(StockProduct dto) throws GeneralException, GeneralWarning;

    StockProduct update(Long id, StockProduct dto) throws GeneralException, GeneralWarning;

    StockProduct delete(Long id) throws GeneralException, GeneralWarning;

    List<StockProduct> getByFoodId(Long id);

}
