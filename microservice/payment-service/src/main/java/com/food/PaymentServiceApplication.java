package com.food;

import com.food.event.OrderEvent;
import com.food.service.OrderService;
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
public class PaymentServiceApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Autowired
    private OrderService orderService;

    @KafkaListener(id = "orders", topics = EventUtil.ORDERS, groupId = "payment")
    public void onEvent(OrderEvent order) {
        log.info("Received: {}", order);

        if (order.getStatus().equals(EventUtil.STATUS_NEW))
            orderService.reserve(order);
        else
            orderService.confirm(order);
    }
}