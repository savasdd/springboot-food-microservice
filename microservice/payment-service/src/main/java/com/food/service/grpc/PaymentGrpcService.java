package com.food.service.grpc;

import com.food.grpc.payment.PaymentRequest;
import com.food.grpc.payment.PaymentResponse;
import com.food.grpc.payment.PaymentServiceGrpc;
import com.food.service.PaymentService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class PaymentGrpcService extends PaymentServiceGrpc.PaymentServiceImplBase {

    private final PaymentService service;

    public PaymentGrpcService(PaymentService service) {
        this.service = service;
    }

    @Override
    public void getPayment(PaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        try {
            var payment = service.getPaymentByStock(request.getStockId());
            responseObserver.onNext(!payment.isEmpty() ? PaymentResponse.newBuilder().setPaymentId(payment.get(0).getId()).setAmountAvailable(payment.get(0).getAmountAvailable().doubleValue()).setStatus(200).build() : PaymentResponse.newBuilder().setStatus(400).build());
            responseObserver.onCompleted();

            log.info("Call PaymentGrpcService");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
