package com.food.service;

import com.food.service.impl.LogServiceImpl;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class LogService {

    private final LogServiceImpl logService;
}
