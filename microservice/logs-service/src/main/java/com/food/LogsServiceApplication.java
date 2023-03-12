package com.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LogsServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LogsServiceApplication.class,args);
    }
}