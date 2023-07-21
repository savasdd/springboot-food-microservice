package com.food.service;

import com.food.model.Payment;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    List<Payment> getAll();

    Payment create(Payment dto);

    Payment update(UUID id, Payment dto);

    void delete(UUID id);
}
