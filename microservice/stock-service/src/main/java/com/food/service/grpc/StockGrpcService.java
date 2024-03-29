package com.food.service.grpc;

import com.food.grpc.stock.StockRequest;
import com.food.grpc.stock.StockResponse;
import com.food.grpc.stock.StockServiceGrpc;
import com.food.service.StockProductService;
import com.food.service.StockService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class StockGrpcService extends StockServiceGrpc.StockServiceImplBase {

    private final StockProductService service;

    public StockGrpcService(StockProductService service) {
        this.service = service;
    }

    @Override
    public void getStock(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        try {
            var stock = service.getByFoodId(request.getFoodId());
            responseObserver.onNext(!stock.isEmpty() ? StockResponse.newBuilder().setStockId(stock.get(0).getStock().getId()).setAvailableItems(stock.get(0).getStock().getAvailableItems()).setStatus(200).build() : StockResponse.newBuilder().setStatus(400).build());
            responseObserver.onCompleted();

            log.info("Call StockGrpcService");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
