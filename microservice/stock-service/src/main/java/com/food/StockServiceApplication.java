package com.food;

import com.food.event.OrderEvent;
import com.food.service.impl.OrderServiceImpl;
import com.food.utils.EventUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableKafka
public class StockServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockServiceApplication.class, args);
    }

    @Autowired
    private OrderServiceImpl orderService;

    @KafkaListener(id = "orders", topics = EventUtil.ORDERS, groupId = "stock")
    public void onEvent(OrderEvent order) {
        log.info("Received: {}", order);

        if (order.getStatus().equals("NEW"))
            orderService.reserve(order);
        else
            orderService.confirm(order);
    }

}