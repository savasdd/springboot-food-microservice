package com.food.service.impl;

import com.food.enums.EPaymentType;
import com.food.event.OrderEvent;
import com.food.service.OrderService;
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

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final KafkaTemplate<String, OrderEvent> template;
    private final StreamsBuilderFactoryBean kafkaStreamsFactory;

    public OrderServiceImpl(KafkaTemplate<String, OrderEvent> template, StreamsBuilderFactoryBean kafkaStreamsFactory) {
        this.template = template;
        this.kafkaStreamsFactory = kafkaStreamsFactory;
    }

    @Override
    public OrderEvent create(OrderEvent order) {
        order.setId(UUID.randomUUID().toString());
        template.send(EventUtil.ORDERS, order.getId(), order);
        log.info("Sent: {}", order);
        return order;
    }

    @Override
    public List<OrderEvent> getAll() {
        List<OrderEvent> orders = new ArrayList<>();
        ReadOnlyKeyValueStore<Long, OrderEvent> store = kafkaStreamsFactory
                .getKafkaStreams()
                .store(StoreQueryParameters.fromNameAndType(EventUtil.ORDERS,
                        QueryableStoreTypes.keyValueStore()));
        KeyValueIterator<Long, OrderEvent> it = store.all();
        it.forEachRemaining(kv -> orders.add(kv.value));
        return orders;
    }

    @Override
    public OrderEvent confirm(OrderEvent orderPayment, OrderEvent orderStock) {
        OrderEvent o = new OrderEvent(orderPayment.getId(),
                orderPayment.getPaymentId(),
                orderPayment.getStockId(),
                orderPayment.getStockCount(),
                orderPayment.getAmount());
        if (orderPayment.getStatus().equals(EPaymentType.ACCEPT) && orderStock.getStatus().equals(EPaymentType.ACCEPT)) {
            o.setStatus(EPaymentType.CONFIRMED);
        } else if (orderPayment.getStatus().equals(EPaymentType.REJECT) && orderStock.getStatus().equals(EPaymentType.REJECT)) {
            o.setStatus(EPaymentType.REJECT);
        } else if (orderPayment.getStatus().equals(EPaymentType.REJECT) || orderStock.getStatus().equals(EPaymentType.REJECT)) {
            String source = orderPayment.getStatus().equals(EPaymentType.REJECT) ? "PAYMENT" : "STOCK";
            o.setStatus(EPaymentType.ROLLBACK);
            o.setSource(source);
        }
        return o;
    }
}
