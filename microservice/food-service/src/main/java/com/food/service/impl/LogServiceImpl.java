package com.food.service.impl;

import com.food.dto.LogFood;
import com.food.event.LogFoodEvent;
import com.food.utils.FoodUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class LogServiceImpl {

    private final WebClient.Builder webClient;
    private final KafkaTemplate<String, LogFoodEvent> kafkaTemplate;

    public void producerLog(LogFood dto){
        LogFoodEvent event = LogFoodEvent.builder().log(dto).status(200).message("food kafka log").build();
        kafkaTemplate.send(FoodUtils.FOOD_LOG, event);
        log.info("create food logs");
    }

    public void sendLog(LogFood dto){

        var response = webClient.build().post()
                .uri(FoodUtils.LOG_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(dto))
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        if(response)
            log.info("create food logs");
    }
}
