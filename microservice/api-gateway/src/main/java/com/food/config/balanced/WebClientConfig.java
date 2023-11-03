//package com.food.config.balanced;
//
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.function.client.WebClient;
//
//@Configuration
////@LoadBalancerClient(name = "user-service22", configuration = ServerInstanceConfig.class)
//class WebClientConfig {
//    @LoadBalanced
//    @Bean
//    WebClient.Builder webClientBuilder() {
//        return WebClient.builder();
//    }
//}