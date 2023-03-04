package com.food.service.impl;

import com.food.event.AccountEvent;
import com.food.event.FoodEvent;
import com.food.utils.AccountUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountServiceImpl {

    private final KafkaTemplate<String,FoodEvent> kafkaTemplate;

    @KafkaListener(topics = AccountUtils.FOOD, groupId = AccountUtils.GROUP_ID)
    public void consumeFood(AccountEvent event) {
        log.info(String.format("Message recieved food -> %s", event.getName()));
    }

    public void sendFood(){
        FoodEvent foodEvent=new FoodEvent();
        foodEvent.setName("Elma hesabı yapıldı:)");
        kafkaTemplate.send(AccountUtils.ACCOUNT,foodEvent);
    }
}
