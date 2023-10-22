package com.food.service.impl;

import com.food.config.rabbit.RabbitConfig;
import com.food.enums.ELogType;
import com.food.event.LogEvent;
import com.food.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    private final RabbitTemplate template;
    private final RabbitConfig config;

    public LogServiceImpl(RabbitTemplate template, RabbitConfig config) {
        this.template = template;
        this.config = config;
    }

    @Override
    public void eventLog(String service, List<Object> body, Integer status, ELogType logType) {
        var event = LogEvent.builder().username("savas.dede").message("log event").body(body).service(service).status(status).logType(logType).build();

        template.convertAndSend(config.exchange, config.routing, event);
        log.info("send rabbit log");
    }

}
