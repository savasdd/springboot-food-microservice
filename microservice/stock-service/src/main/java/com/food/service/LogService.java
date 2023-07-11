package com.food.service;

import com.food.dto.LogStock;

public interface LogService {
    void producerLog(LogStock dto);

    void sendLog(LogStock dto);
}
