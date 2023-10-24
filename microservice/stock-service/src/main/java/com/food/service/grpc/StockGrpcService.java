package com.food.service.grpc;

import com.food.grpc.StockServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class StockGrpcService extends StockServiceGrpc.StockServiceImplBase {
}
