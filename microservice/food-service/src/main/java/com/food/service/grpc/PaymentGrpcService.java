package com.food.service.grpc;

import com.food.grpc.payment.PaymentResponse;

public interface PaymentGrpcService {
    PaymentResponse getPayment(String stockId);
}
