package com.food.service;

import com.food.dto.LogFood;
import com.food.enums.ELogType;

import java.util.List;

public interface LogService {

    void producerLog(LogFood dto);

    void eventLog(String service, List<Object> body, Integer status, ELogType logType);

    void eventLogJson(String service, List<Object> body, Integer status);
}
