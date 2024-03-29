package com.food;

import com.food.db.CreatingDatabase;
import com.food.enums.EPaymentType;
import com.food.event.OrderEvent;
import com.food.repository.base.BaseRepositoryImpl;
import com.food.service.OrderService;
import com.food.utils.EventUtil;
import com.food.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
@EnableEurekaClient
@EnableKafka
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class)
public class StockServiceApplication {
    public static void main(String[] args) {
        CreatingDatabase.builder().build();
        SpringApplication.run(StockServiceApplication.class, args);
    }

    @Autowired
    private OrderService orderService;

    @KafkaListener(id = "orders", topics = EventUtil.ORDERS, groupId = "group-id")
    public void onEvent(String order) {
        log.info("Received: {}", order);
        var event = JsonUtil.fromJson(order, OrderEvent.class);

        if (event.getStatus().equals(EPaymentType.NEW))
            orderService.reserve(event);
        else
            orderService.confirm(event);
    }

}