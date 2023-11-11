package com.food.service;

import com.food.dto.FoodFileDto;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;

import java.io.InputStream;
import java.util.List;

public interface FoodFileService {

    List<FoodFileDto> getListObjects(Long foodId) throws GeneralException, GeneralWarning;

    InputStream getObjects(String fileName);

    FoodFileDto uploadFile(FoodFileDto dto) throws GeneralException, GeneralWarning;

    String deleteObjects(String fileName) throws GeneralException, GeneralWarning;
}
