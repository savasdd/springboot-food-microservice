package com.food.service;

import com.food.dto.LogCategory;


public interface LogService {

    public void producerLog(LogCategory dto);

    public void sendLog(LogCategory dto);
}
