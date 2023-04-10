package com.food.service;

import com.food.dto.LogAccount;
import com.food.service.impl.LogServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

public interface LogService {

    public void producerLog(LogAccount dto);
    public void sendLog(LogAccount dto);
}
