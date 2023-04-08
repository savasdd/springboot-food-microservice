package com.food.service;

import com.food.dto.FoodDto;
import com.food.dto.StockDto;
import com.food.event.StockEvent;

import java.util.List;
import java.util.UUID;

public interface FoodService {

    public List<FoodDto> getAll();
    public FoodDto create(FoodDto dto);

    public FoodDto update(UUID id, FoodDto dto);
    public FoodDto delete(UUID id);

    public StockEvent producerStockCreate(UUID foodId, StockDto dto);
}
