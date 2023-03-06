package com.food.service.impl;

import com.food.event.AccountDto;
import com.food.event.FoodDto;
import com.food.utils.FoodUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
@RequiredArgsConstructor
public class FoodServiceImpl {

    private final KafkaTemplate<String, AccountDto> kafkaTemplate;

    @KafkaListener(topics = FoodUtils.ACCOUNT, groupId = FoodUtils.GROUP_ID)
    public void consumeAccount(FoodDto dto) {
        log.info("Kafka received food {} ",dto);
    }

    public void sendAccount(){
        AccountDto dto=new AccountDto();
        dto.setFoodName("Şeftali");
        dto.setFoodCount(20);
        dto.setFoodPrice(new BigDecimal(125.86));
        kafkaTemplate.send(FoodUtils.FOOD,dto);
    }

}
