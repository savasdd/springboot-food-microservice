package com.food.service.impl;

import com.food.event.OrderEvent;
import com.food.repository.StockRepository;
import com.food.service.OrderService;
import com.food.utils.EventUtil;
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
    private final KafkaTemplate<String, OrderEvent> template;

    public OrderServiceImpl(StockRepository repository, KafkaTemplate<String, OrderEvent> template) {
        this.repository = repository;
        this.template = template;
    }

    @Override
    public void reserve(OrderEvent order) {
        var product = repository.findById(UUID.fromString(order.getStockId())).orElseThrow();
        log.info("Found: {}", product.getStockId());

        if (order.getStatus().equals(EventUtil.STATUS_NEW)) {
            if (order.getStockCount() < product.getAvailableItems()) {
                product.setReservedItems(product.getReservedItems() + order.getStockCount());
                product.setAvailableItems(product.getAvailableItems() - order.getStockCount());
                product.setPrice(order.getAmount());
                product.setTransactionDate(new Date());
                product.setStatus(EventUtil.STATUS_ACCEPT);
                order.setStatus(EventUtil.STATUS_ACCEPT);
            } else {
                product.setStatus(EventUtil.STATUS_REJECT);
                order.setStatus(EventUtil.STATUS_REJECT);
            }

            repository.save(product);
            template.send(EventUtil.STOCK_ORDERS, order.getId(), order);
            log.info("Sent: {}", order);
        }
    }

    @Override
    public void confirm(OrderEvent order) {
        var product = repository.findById(UUID.fromString(order.getStockId())).orElseThrow();
        log.info("Found: {}", product.getStockId());

        if (order.getStatus().equals(EventUtil.STATUS_CONFIRMED)) {
            product.setReservedItems(product.getReservedItems() - order.getStockCount());
            product.setStatus(EventUtil.STATUS_CONFIRMED);
        } else if (order.getStatus().equals(EventUtil.STATUS_ROLLBACK) && !order.getSource().equals(SOURCE)) {
            product.setReservedItems(product.getReservedItems() - order.getStockCount());
            product.setAvailableItems(product.getAvailableItems() + order.getStockCount());
            product.setStatus(EventUtil.STATUS_ROLLBACK);
        }

        repository.save(product);
    }

}
