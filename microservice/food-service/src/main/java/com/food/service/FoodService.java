package com.food.service;

import com.food.dto.FoodDto;

import java.util.List;
import java.util.UUID;

public interface FoodService {

    List<FoodDto> getAll();

    FoodDto create(FoodDto dto);

    FoodDto update(UUID id, FoodDto dto);

    FoodDto delete(UUID id);

}
