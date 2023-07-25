package com.food.service;

import com.food.dto.StockDto;

import java.util.List;
import java.util.UUID;

public interface StockService {

    List<StockDto> getAll();

    StockDto create(StockDto dto);

    StockDto update(UUID id, StockDto dto);

    StockDto delete(UUID id);
}
