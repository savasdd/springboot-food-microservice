package com.food.service;

import com.food.event.OrderEvent;

public interface OrderService {

    void reserve(OrderEvent order);

    void confirm(OrderEvent order);
}
