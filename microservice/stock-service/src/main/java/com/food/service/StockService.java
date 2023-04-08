package com.food.service;

import com.food.dto.StockDto;
import com.food.service.impl.StockServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface StockService {

    public List<StockDto> getAll(UUID foodId);
    public StockDto create(UUID foodId,StockDto dto);
    public StockDto update(UUID foodId,UUID id,StockDto dto);
    public StockDto delete(UUID foodId,UUID id);
}
