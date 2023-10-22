package com.food.service.impl;

import com.food.config.rabbit.RabbitConfig;
import com.food.enums.ELogType;
import com.food.event.LogEvent;
import com.food.service.LogService;
import com.food.utils.EventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    private final WebClient.Builder webClient;
    private final RabbitTemplate template;
    private final RabbitConfig config;

    public LogServiceImpl(WebClient.Builder webClient, RabbitTemplate template, RabbitConfig config) {
        this.webClient = webClient;
        this.template = template;
        this.config = config;
    }

    @Override
    public void eventLog(String service, List<Object> body, Integer status, ELogType logType) {
        var event = LogEvent.builder().username("savas.dede").message("log event").body(body).service(service).status(status).logType(logType).build();

        template.convertAndSend(config.exchange, config.routing, event);
        log.info("send rabbit log");
    }

    @Override
    public void eventLogJson(String service, List<Object> body, Integer status) {
        var event = LogEvent.builder().username("savas.dede").message("log event").body(body).service(service).status(status).logType(ELogType.FOOD).build();

        template.convertAndSend(config.exchange, config.routingJson, event);
        log.info("send rabbit log");
    }



    public void sendLog(Object dto) {
        var response = webClient.build().post()
                .uri(EventUtil.LOG_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (response)
            log.info("create food logs");
    }
}
