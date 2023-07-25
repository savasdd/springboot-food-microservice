package com.food.service.impl;

import com.food.dto.LogStock;
import com.food.event.LogStockEvent;
import com.food.service.LogService;
import com.food.utils.EventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class LogServiceImpl implements LogService {
    private final WebClient.Builder webClient;
    private final RabbitTemplate template;
    private final Queue queue;

    public LogServiceImpl(WebClient.Builder webClient, RabbitTemplate template, Queue queue) {
        this.webClient = webClient;
        this.template = template;
        this.queue = queue;
    }

    @Override
    public void producerLog(LogStock dto) {
        LogStockEvent event = LogStockEvent.builder().log(dto).status(200).message("stock kafka log").build();
        template.convertAndSend(queue.getName(), event);
        log.info("create stock logs");
    }


    public void sendLog(LogStock dto) {

        var response = webClient.build().post()
                .uri(EventUtil.LOG_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if (response)
            log.info("create stock logs");
    }
}
