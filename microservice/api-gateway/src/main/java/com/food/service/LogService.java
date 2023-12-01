package com.food.service;

import com.food.enums.ELogType;
import com.food.event.LogEvent;

import java.util.List;

public interface LogService {

    void eventLog(LogEvent event);

    void eventLogJson(String service, List<Object> body, Integer status);
}
