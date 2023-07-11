package com.food.service;

import com.food.dto.StockDto;

import java.util.List;
import java.util.UUID;

public interface StockService {

    List<StockDto> getAll(UUID foodId);

    StockDto create(UUID foodId, StockDto dto);

    StockDto update(UUID foodId, UUID id, StockDto dto);

    StockDto delete(UUID foodId, UUID id);
}
