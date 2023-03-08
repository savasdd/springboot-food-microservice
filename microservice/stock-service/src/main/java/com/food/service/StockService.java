package com.food.service;

import com.food.service.impl.StockServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockServiceImpl stockService;
}
