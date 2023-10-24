package com.food.service.grpc;

import com.food.grpc.StockRequest;
import com.food.grpc.StockResponse;
import com.food.grpc.StockServiceGrpc;
import com.food.service.StockService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class StockGrpcService extends StockServiceGrpc.StockServiceImplBase {

    private final StockService service;

    public StockGrpcService(StockService service) {
        this.service = service;
    }

    @Override
    public void getStock(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        try {
            var stock = service.getStockByFoodId(request.getFoodId());
            responseObserver.onNext(!stock.isEmpty() ? StockResponse.newBuilder().setStockId(stock.get(0).getStockId().toString()).setAvailableItems(stock.get(0).getAvailableItems()).build() : null);
            responseObserver.onCompleted();

            log.info("Stock Grpc");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
