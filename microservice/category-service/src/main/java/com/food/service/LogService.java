package com.food.service;

import com.food.service.impl.LogServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class LogService {

    private final LogServiceImpl logService;
}
