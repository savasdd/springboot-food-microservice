package com.food.service.impl;

import com.food.dto.Order;
import com.food.repository.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class OrderServiceImpl {

    private static final String SOURCE = "stock";
    private final StockRepository repository;
    private final KafkaTemplate<String, Order> template;

    public OrderServiceImpl(StockRepository repository, KafkaTemplate<String, Order> template) {
        this.repository = repository;
        this.template = template;
    }

    public void reserve(Order order) {
        var product = repository.findById(UUID.fromString(order.getStockId())).orElseThrow();
        log.info("Found: {}", product);

        if (order.getStatus().equals("NEW")) {
            if (order.getStockCount() < product.getAvailableItems()) {
                product.setReservedItems(product.getReservedItems() + order.getStockCount());
                product.setAvailableItems(product.getAvailableItems() - order.getStockCount());
                //product.setPrice(order.getPrice());
                order.setStatus("ACCEPT");
                repository.save(product);
            } else {
                order.setStatus("REJECT");
            }

            template.send("stock-orders", order.getId(), order);
            log.info("Sent: {}", order);
        }
    }

    public void confirm(Order order) {
        var product = repository.findById(UUID.fromString(order.getStockId())).orElseThrow();
        log.info("Found: {}", product);

        if (order.getStatus().equals("CONFIRMED")) {
            product.setReservedItems(product.getReservedItems() - order.getStockCount());
            repository.save(product);
        } else if (order.getStatus().equals("ROLLBACK") && !order.getSource().equals(SOURCE)) {
            product.setReservedItems(product.getReservedItems() - order.getStockCount());
            product.setAvailableItems(product.getAvailableItems() + order.getStockCount());
            repository.save(product);
        }
    }

}
