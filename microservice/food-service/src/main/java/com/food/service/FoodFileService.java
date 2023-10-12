package com.food.service;

import com.food.dto.FoodFileDto;

import java.io.InputStream;
import java.util.List;

public interface FoodFileService {

    List<FoodFileDto> getListObjects(String foodId);

    InputStream getObjects(String fileName);

    FoodFileDto uploadFile(FoodFileDto dto);
}
