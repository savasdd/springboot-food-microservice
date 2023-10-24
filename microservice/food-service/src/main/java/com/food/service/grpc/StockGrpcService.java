package com.food.service.grpc;

import com.food.grpc.StockResponse;

public interface StockGrpcService {
    StockResponse getStock(String foodId);
}
