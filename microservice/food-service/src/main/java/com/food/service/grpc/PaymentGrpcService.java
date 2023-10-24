package com.food.service.grpc;

import com.food.grpc.PaymentResponse;

public interface PaymentGrpcService {
    PaymentResponse getPayment(String stockId);
}
