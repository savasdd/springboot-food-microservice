package com.food.service.impl;

import com.food.enums.EPaymentType;
import com.food.event.OrderEvent;
import com.food.repository.StockRepository;
import com.food.service.OrderService;
import com.food.utils.EventUtil;
import com.food.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private static final String SOURCE = "stock";
    private final StockRepository repository;
    private final KafkaTemplate<String, String> template;

    public OrderServiceImpl(StockRepository repository, KafkaTemplate<String, String> template) {
        this.repository = repository;
        this.template = template;
    }

    @Override
    public void reserve(OrderEvent order) {
        var product = repository.findById(UUID.fromString(order.getStockId())).orElseThrow(() -> new RuntimeException("Not Found Stock"));
        log.info("Found: {}", product.getStockId());

        if (order.getStatus().equals(EPaymentType.NEW)) {
            if (order.getStockCount() < product.getAvailableItems()) {
                product.setReservedItems(product.getReservedItems() + order.getStockCount());
                product.setAvailableItems(product.getAvailableItems() - order.getStockCount());
                product.setPrice(order.getAmount());
                product.setTransactionDate(new Date());
                product.setStatus(EPaymentType.ACCEPT);
                order.setStatus(EPaymentType.ACCEPT);
            } else {
                product.setStatus(EPaymentType.REJECT);
                order.setStatus(EPaymentType.REJECT);
            }

            String json = JsonUtil.toJson(order);
            repository.save(product);
            template.send(EventUtil.STOCK_ORDERS, order.getId(), json);
            log.info("Sent: {}", order);
        }
    }

    @Override
    public void confirm(OrderEvent order) {
        var product = repository.findById(UUID.fromString(order.getStockId())).orElseThrow(() -> new RuntimeException("Not Found Stock"));
        log.info("Found: {}", product.getStockId());

        if (order.getStatus().equals(EPaymentType.CONFIRMED)) {
            product.setReservedItems(product.getReservedItems() - order.getStockCount());
            product.setStatus(EPaymentType.CONFIRMED);
        } else if (order.getStatus().equals(EPaymentType.ROLLBACK) && !order.getSource().equals(SOURCE)) {
            product.setReservedItems(product.getReservedItems() - order.getStockCount());
            product.setAvailableItems(product.getAvailableItems() + order.getStockCount());
            product.setStatus(EPaymentType.ROLLBACK);
        }

        repository.save(product);
    }

}
