package com.food;

import com.food.event.LogEvent;
import com.food.utils.EventUtil;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
public class LogsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogsServiceApplication.class, args);
    }

}