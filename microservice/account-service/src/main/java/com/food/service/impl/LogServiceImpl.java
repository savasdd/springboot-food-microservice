package com.food.service.impl;

import com.food.dto.LogAccount;
import com.food.service.LogService;
import com.food.utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class LogServiceImpl implements LogService {
    private final WebClient.Builder webClient;

    public LogServiceImpl(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @Override
    public void sendLog(LogAccount dto){

        var response = webClient.build().post()
                .uri(AccountUtils.LOG_URL)
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
