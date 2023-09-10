package com.food.service.impl;

import com.food.dto.UserFileDto;
import com.food.service.UserFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserFileServiceImpl implements UserFileService {
    @Override
    public void userFileUpload(UserFileDto dto) {
        System.out.println(dto);

    }
}
