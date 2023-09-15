package com.food.service;

import com.food.dto.UserFileDto;

import java.io.InputStream;
import java.util.List;

public interface UserFileService {

    List<UserFileDto> getListObjects();

    InputStream getObjects(String fileName);

    UserFileDto uploadFile(UserFileDto dto);
}
