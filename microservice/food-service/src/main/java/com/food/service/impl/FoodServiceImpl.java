package com.food.service.impl;

import com.food.event.AccountEvent;
import com.food.utils.FoodUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FoodServiceImpl {


    @KafkaListener(topics = FoodUtils.TOPIC_NAME_ACCOUNT, groupId = FoodUtils.GROUP_ID)
    public void consumeAccount(AccountEvent event) {
        log.info(String.format("Message recieved account -> %s", event.getName()));
    }

}
