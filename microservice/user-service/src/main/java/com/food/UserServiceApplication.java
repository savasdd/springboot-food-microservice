package com.food;

import com.food.db.CreatingDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {
    public static void main(String[] args) {
        CreatingDatabase.builder().build();
        SpringApplication.run(UserServiceApplication.class, args);
    }
}