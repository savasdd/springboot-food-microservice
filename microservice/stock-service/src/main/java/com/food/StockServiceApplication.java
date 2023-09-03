package com.food;

import com.food.enums.EPaymentType;
import com.food.event.OrderEvent;
import com.food.service.OrderService;
import com.food.utils.EventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EnableKafka
@EnableRabbit
public class StockServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }

    @Autowired
    private OrderService orderService;

    @KafkaListener(id = "orders", topics = EventUtil.ORDERS, groupId = "stock")
    public void onEvent(OrderEvent order) {
        log.info("Received: {}", order);

        if (order.getStatus().equals(EPaymentType.NEW))
            orderService.reserve(order);
        else
            orderService.confirm(order);
    }

}