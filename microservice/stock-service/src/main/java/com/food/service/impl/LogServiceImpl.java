package com.food.service.impl;

import com.food.dto.LogStock;
import com.food.event.LogStockEvent;
import com.food.service.LogService;
import com.food.utils.EventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class LogServiceImpl implements LogService {

    private final WebClient.Builder webClient;
    private final KafkaTemplate<String, LogStockEvent> kafkaTemplate;

    public LogServiceImpl(WebClient.Builder webClient, KafkaTemplate<String, LogStockEvent> kafkaTemplate) {
        this.webClient = webClient;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void producerLog(LogStock dto) {
        LogStockEvent event = LogStockEvent.builder().log(dto).status(200).message("stock kafka log").build();
        kafkaTemplate.send(EventUtil.STOCK_LOG, event);
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
