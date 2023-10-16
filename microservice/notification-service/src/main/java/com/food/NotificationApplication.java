package com.food;

import com.food.event.OrderEvent;
import com.food.service.EmailService;
import com.food.utils.EventUtil;
import com.food.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Autowired
    private EmailService service;

    @KafkaListener(id = "orders", topics = EventUtil.ORDERS_NOTIFICATION, groupId = EventUtil.GROUP_ID)
    public void onEvent(String order) {
        log.info("Received: {}", order);
        var event = JsonUtil.fromJson(order, OrderEvent.class);
        service.confirm(event);
    }
}