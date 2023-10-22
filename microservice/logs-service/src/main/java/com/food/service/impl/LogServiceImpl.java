package com.food.service.impl;

import com.food.event.LogEvent;
import com.food.model.LogCategory;
import com.food.model.LogFood;
import com.food.model.LogPayment;
import com.food.model.LogStock;
import com.food.service.CategoryService;
import com.food.service.FoodService;
import com.food.service.PaymentService;
import com.food.service.StockService;
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

    public LogServiceImpl(ModelMapper modelMapper, FoodService foodService, CategoryService categoryService, PaymentService paymentService, StockService stockService) {
        this.modelMapper = modelMapper;
        this.foodService = foodService;
        this.categoryService = categoryService;
        this.paymentService = paymentService;
        this.stockService = stockService;
    }

    @RabbitListener(queues = {"${rabbit.queue}"})
    @Transactional
    public void eventLog(LogEvent event) {

        switch (event.getLogType()) {
            case FOOD:
                foodService.createLogFood(LogFood.builder().username(event.getUsername()).service(event.getService()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            case STOCK:
                stockService.createLogStock(LogStock.builder().username(event.getUsername()).service(event.getService()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            case PAYMENT:
                paymentService.createLogPayment(LogPayment.builder().username(event.getUsername()).service(event.getService()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            case CATEGORY:
                categoryService.createLogCategory(LogCategory.builder().username(event.getUsername()).service(event.getService()).logType(event.getLogType()).status(event.getStatus()).body(event.getBody()).build());
                break;
            case USER:
                break;
            default:
                log.info("undefined type");
                break;
        }

        log.info(event.getMessage() + " {} {}", event.getLogType(), event.getStatus());
    }


}
