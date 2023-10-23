package com.food.service;

import com.food.dto.StockDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Stock;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;

public interface StockService {

    LoadResult<Stock> getAll(DataSourceLoadOptions<Stock> loadOptions) throws GeneralException, GeneralWarning;

    List<StockDto> getAll() throws GeneralException, GeneralWarning;

    StockDto getById(String id) throws GeneralException, GeneralWarning;

    StockDto create(StockDto dto) throws GeneralException, GeneralWarning;

    StockDto update(String id, StockDto dto) throws GeneralException, GeneralWarning;

    StockDto delete(String id) throws GeneralException, GeneralWarning;

    List<Stock> getStockByFoodId(String id) throws GeneralException, GeneralWarning;
}
