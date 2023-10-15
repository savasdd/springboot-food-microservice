package com.food.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.event.OrderEvent;

import java.util.List;

public interface OrderService {

    OrderEvent create(OrderEvent order) throws JsonProcessingException;

    List<OrderEvent> getAll();

    OrderEvent confirm(OrderEvent orderPayment, OrderEvent orderStock);

    void confirmPayment(OrderEvent event);
}
