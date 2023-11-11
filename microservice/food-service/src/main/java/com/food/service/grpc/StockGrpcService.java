package com.food.service.grpc;

import com.food.grpc.stock.StockResponse;

public interface StockGrpcService {
    StockResponse getStock(Long foodId);
}
