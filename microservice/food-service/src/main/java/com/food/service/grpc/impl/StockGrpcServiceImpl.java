package com.food.service.grpc.impl;

import com.food.grpc.stock.StockRequest;
import com.food.grpc.stock.StockResponse;
import com.food.grpc.stock.StockServiceGrpc;
import com.food.service.grpc.StockGrpcService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StockGrpcServiceImpl implements StockGrpcService {

    @GrpcClient("grpc-stock-service")
    StockServiceGrpc.StockServiceBlockingStub serviceBlockingStub;

    @GrpcClient("grpc-stock-service")
    StockServiceGrpc.StockServiceStub stockServiceStub;

    @Override
    public StockResponse getStock(String foodId) {
        var response = serviceBlockingStub.getStock(StockRequest.newBuilder().setFoodId(foodId).build());
        return response;
    }
}
