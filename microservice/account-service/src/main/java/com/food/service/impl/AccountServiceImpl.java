package com.food.service.impl;

import com.food.dto.AccountDto;
import com.food.dto.FoodDto;
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

    private final KafkaTemplate<String, FoodDto> kafkaTemplate;

    @KafkaListener(topics = AccountUtils.FOOD, groupId = AccountUtils.GROUP_ID)
    public void consumeFood(AccountDto dto) {
        log.info("Kafka received account {} ",dto);
    }

    public void sendFood(){
        FoodDto dto=new FoodDto();
        dto.setFoodName("Şeftali");
        dto.setFoodType("Meyve");
        dto.setDescription("Tüylü şeftali!");

        kafkaTemplate.send(AccountUtils.ACCOUNT,dto);
    }
}
