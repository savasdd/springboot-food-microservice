package com.food.service;

import com.food.dto.LogFood;

import java.util.List;

public interface LogService {

    void producerLog(LogFood dto);

    void eventLog(String service, List<Object> body, Integer status);

    void eventLogJson(String service, List<Object> body, Integer status);
}
