package com.food.service;

import com.food.dto.StockDto;
import com.food.model.Stock;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;
import java.util.UUID;

public interface StockService {

    LoadResult<Stock> getAll(DataSourceLoadOptions<Stock> loadOptions);

    List<StockDto> getAll();

    StockDto getById(String id);

    StockDto create(StockDto dto);

    StockDto update(String id, StockDto dto);

    StockDto delete(String id);
}
