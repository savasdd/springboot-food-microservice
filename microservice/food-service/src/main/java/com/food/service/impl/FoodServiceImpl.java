package com.food.service.impl;

import com.food.event.AccountEvent;
import com.food.event.FoodEvent;
import com.food.utils.FoodUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FoodServiceImpl {

    private final KafkaTemplate<String,AccountEvent> kafkaTemplate;

    @KafkaListener(topics = FoodUtils.ACCOUNT, groupId = FoodUtils.GROUP_ID)
    public void consumeAccount(FoodEvent event) {
        log.info(String.format("Message recieved account -> %s", event.getName()));
    }

    public void sendAccount(){
        AccountEvent dto=new AccountEvent();
        dto.setName("Elma fiyatı: 120.78");
        kafkaTemplate.send(FoodUtils.FOOD,dto);
    }

}
