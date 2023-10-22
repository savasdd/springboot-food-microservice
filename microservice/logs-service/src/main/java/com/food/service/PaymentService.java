package com.food.service;

import com.food.model.LogPayment;

import java.util.List;

public interface PaymentService {

    List<LogPayment> getLogPayment();

    LogPayment getOneLogPayment(String id);

    LogPayment createLogPayment(LogPayment dto);

    void deleteLogPayment(String id);

}
