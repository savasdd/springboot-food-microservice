package com.food.service;

import com.food.dto.LogFood;
import com.food.service.impl.LogServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

public interface LogService {

    public void producerLog(LogFood dto);

    public void sendLog(LogFood dto);
}
