package com.food.service.impl;

import com.food.config.rabbit.RabbitConfig;
import com.food.enums.ELogType;
import com.food.event.LogEvent;
import com.food.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
    public void eventLog(LogEvent event) {
        template.convertAndSend(config.exchange, config.routing, event);
        log.info("send rabbit log");
    }

    @Override
    public void eventLogJson(String service, List<Object> body, Integer status) {
        var event = LogEvent.builder().username("savas.dede").body(body).status(status).logType(ELogType.FOOD).build();

        template.convertAndSend(config.exchange, config.routingJson, event);
        log.info("send rabbit log");
    }


}
