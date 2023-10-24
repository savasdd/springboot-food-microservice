package com.food.service.grpc.impl;

import com.food.grpc.PaymentRequest;
import com.food.grpc.PaymentResponse;
import com.food.grpc.PaymentServiceGrpc;
import com.food.service.grpc.PaymentGrpcService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class PaymentGrpcServiceImpl implements PaymentGrpcService {
    @GrpcClient("grpc-payment-service")
    PaymentServiceGrpc.PaymentServiceBlockingStub serviceBlockingStub;

    @Override
    public PaymentResponse getPayment(String stockId) {
        var response = serviceBlockingStub.getPayment(PaymentRequest.newBuilder().setStockId(stockId).build());
        return response;
    }
}
