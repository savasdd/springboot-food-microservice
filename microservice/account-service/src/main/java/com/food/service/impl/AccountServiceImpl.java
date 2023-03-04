package com.food.service.impl;

import com.food.event.FoodEvent;
import com.food.utils.AccountUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AccountServiceImpl {

    @KafkaListener(topics = AccountUtils.TOPIC_NAME_FOOD, groupId = AccountUtils.GROUP_ID)
    public void consumeFood(FoodEvent event) {
        log.info(String.format("Message recieved food -> %s", event.getName()));
    }
}
