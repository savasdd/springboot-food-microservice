package com.food.service;

import com.food.service.impl.FoodServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodServiceImpl foodService;
}
