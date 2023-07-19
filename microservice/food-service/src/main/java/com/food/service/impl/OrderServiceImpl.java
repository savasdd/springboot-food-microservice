package com.food.service.impl;

import com.food.dto.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class OrderServiceImpl {

    private AtomicLong id = new AtomicLong();
    private final KafkaTemplate<String, Order> template;
    private final StreamsBuilderFactoryBean kafkaStreamsFactory;

    public OrderServiceImpl(KafkaTemplate<String, Order> template, StreamsBuilderFactoryBean kafkaStreamsFactory) {
        this.template = template;
        this.kafkaStreamsFactory = kafkaStreamsFactory;
    }

    public Order create(Order order) {
        order.setId(UUID.randomUUID().toString());
        template.send("orders", order.getId(), order);
        log.info("Sent: {}", order);
        return order;
    }

    public List<Order> all() {
        List<Order> orders = new ArrayList<>();
        ReadOnlyKeyValueStore<Long, Order> store = kafkaStreamsFactory
                .getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType("orders",
                        QueryableStoreTypes.keyValueStore()));
        KeyValueIterator<Long, Order> it = store.all();
        it.forEachRemaining(kv -> orders.add(kv.value));
        return orders;
    }

    public Order confirm(Order orderPayment, Order orderStock) {
        Order o = new Order(orderPayment.getId(),
                orderPayment.getCustomerId(),
                orderPayment.getStockId(),
                orderPayment.getStockCount(),
                orderPayment.getPrice());
        if (orderPayment.getStatus().equals("ACCEPT") && orderStock.getStatus().equals("ACCEPT")) {
            o.setStatus("CONFIRMED");
        } else if (orderPayment.getStatus().equals("REJECT") && orderStock.getStatus().equals("REJECT")) {
            o.setStatus("REJECTED");
        } else if (orderPayment.getStatus().equals("REJECT") || orderStock.getStatus().equals("REJECT")) {
            String source = orderPayment.getStatus().equals("REJECT") ? "PAYMENT" : "STOCK";
            o.setStatus("ROLLBACK");
            o.setSource(source);
        }
        return o;
    }
}
