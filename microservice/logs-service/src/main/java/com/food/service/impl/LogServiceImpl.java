package com.food.service.impl;

import com.food.event.LogEvent;
import com.food.model.*;
import com.food.service.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class LogServiceImpl {

    private final ModelMapper modelMapper;
    private final FoodService foodService;
    private final CategoryService categoryService;
    private final PaymentService paymentService;
    private final StockService stockService;
    private final UserService userService;

    public LogServiceImpl(ModelMapper modelMapper, FoodService foodService, CategoryService categoryService, PaymentService paymentService, StockService stockService, UserService userService) {
        this.modelMapper = modelMapper;
        this.foodService = foodService;
        this.categoryService = categoryService;
        this.paymentService = paymentService;
        this.stockService = stockService;
        this.userService = userService;
    }

    @RabbitListener(queues = {"${rabbit.queue}"})
    @Transactional
    public void eventLog(LogEvent event) {

        switch (event.getLogType()) {
            case FOOD:
                foodService.createLogFood(LogFood.builder().username(event.getUsername()).requestId(event.getRequestId()).method(event.getMethod()).url(event.getUrl()).path(event.getPath()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            case STOCK:
                stockService.createLogStock(LogStock.builder().username(event.getUsername()).requestId(event.getRequestId()).method(event.getMethod()).url(event.getUrl()).path(event.getPath()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            case PAYMENT:
                paymentService.createLogPayment(LogPayment.builder().username(event.getUsername()).requestId(event.getRequestId()).method(event.getMethod()).url(event.getUrl()).path(event.getPath()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            case CATEGORY:
                categoryService.createLogCategory(LogCategory.builder().username(event.getUsername()).requestId(event.getRequestId()).method(event.getMethod()).url(event.getUrl()).path(event.getPath()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            case USER:
                userService.createLog(LogUser.builder().username(event.getUsername()).requestId(event.getRequestId()).method(event.getMethod()).url(event.getUrl()).path(event.getPath()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            default:
                log.info("undefined type");
                break;
        }

        log.info("EVENT LOG" + " {} {}", event.getLogType(), event.getMethod());
    }


}
