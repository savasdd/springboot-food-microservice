package com.food.service;

import com.food.event.OrderEvent;

import java.util.List;

public interface OrderService {

    OrderEvent create(OrderEvent order);

    List<OrderEvent> getAll();

    OrderEvent confirm(OrderEvent orderPayment, OrderEvent orderStock);
}
