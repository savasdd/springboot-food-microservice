package com.food.service.impl;

import com.food.event.OrderEvent;
import com.food.utils.EventUtil;
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
    private final KafkaTemplate<String, OrderEvent> template;
    private final StreamsBuilderFactoryBean kafkaStreamsFactory;

    public OrderServiceImpl(KafkaTemplate<String, OrderEvent> template, StreamsBuilderFactoryBean kafkaStreamsFactory) {
        this.template = template;
        this.kafkaStreamsFactory = kafkaStreamsFactory;
    }

    public OrderEvent create(OrderEvent order) {
        order.setId(UUID.randomUUID().toString());
        template.send(EventUtil.ORDERS, order.getId(), order);
        log.info("Sent: {}", order);
        return order;
    }

    public List<OrderEvent> all() {
        List<OrderEvent> orders = new ArrayList<>();
        ReadOnlyKeyValueStore<Long, OrderEvent> store = kafkaStreamsFactory
                .getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType(EventUtil.ORDERS,
                        QueryableStoreTypes.keyValueStore()));
        KeyValueIterator<Long, OrderEvent> it = store.all();
        it.forEachRemaining(kv -> orders.add(kv.value));
        return orders;
    }

    public OrderEvent confirm(OrderEvent orderPayment, OrderEvent orderStock) {
        OrderEvent o = new OrderEvent(orderPayment.getId(),
                orderPayment.getCustomerId(),
                orderPayment.getStockId(),
                orderPayment.getStockCount(),
                orderPayment.getPrice());
        if (orderPayment.getStatus().equals(EventUtil.STATUS_ACCEPT) && orderStock.getStatus().equals(EventUtil.STATUS_ACCEPT)) {
            o.setStatus(EventUtil.STATUS_CONFIRMED);
        } else if (orderPayment.getStatus().equals(EventUtil.STATUS_REJECT) && orderStock.getStatus().equals(EventUtil.STATUS_REJECT)) {
            o.setStatus(EventUtil.STATUS_REJECT);
        } else if (orderPayment.getStatus().equals(EventUtil.STATUS_REJECT) || orderStock.getStatus().equals(EventUtil.STATUS_REJECT)) {
            String source = orderPayment.getStatus().equals(EventUtil.STATUS_REJECT) ? "PAYMENT" : "STOCK";
            o.setStatus(EventUtil.STATUS_ROLLBACK);
            o.setSource(source);
        }
        return o;
    }
}
