package com.food.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.food.enums.EPaymentType;
import com.food.event.OrderEvent;
import com.food.repository.FoodRepository;
import com.food.service.PaymentService;
import com.food.utils.EventUtil;
import com.food.utils.JsonUtil;
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
public class PaymentServiceImpl implements PaymentService {

    private final KafkaTemplate<String, String> template;
    private final StreamsBuilderFactoryBean kafkaStreamsFactory;
    private final FoodRepository repository;

    public PaymentServiceImpl(KafkaTemplate<String, String> template, StreamsBuilderFactoryBean kafkaStreamsFactory, FoodRepository repository) {
        this.template = template;
        this.kafkaStreamsFactory = kafkaStreamsFactory;
        this.repository = repository;
    }

    @Override
    public OrderEvent create(OrderEvent order) throws JsonProcessingException {
        order.setId(UUID.randomUUID().toString());
        order.setMessage("ürün alındı");
        String json = JsonUtil.toJson(order);

        template.send(EventUtil.ORDERS, order.getId(), json);
        log.info("Sent: {}", order);
        return order;
    }

    @Override
    public List<OrderEvent> getAll() {
        List<OrderEvent> orders = new ArrayList<>();
        ReadOnlyKeyValueStore<Long, OrderEvent> store = kafkaStreamsFactory.getKafkaStreams().store(StoreQueryParameters.fromNameAndType(EventUtil.ORDERS, QueryableStoreTypes.keyValueStore()));
        KeyValueIterator<Long, OrderEvent> it = store.all();
        it.forEachRemaining(kv -> orders.add(kv.value));
        return orders;
    }

    @Override
    public OrderEvent confirm(OrderEvent orderPayment, OrderEvent orderStock) {
        OrderEvent o = new OrderEvent(orderPayment.getId(),
                orderPayment.getPaymentId(),
                orderPayment.getStockId(),
                orderPayment.getFoodId(),
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

    @Override
    public void confirmPayment(OrderEvent event) {
        var food = repository.findById(UUID.fromString(event.getFoodId())).orElseThrow(() -> new RuntimeException("Not found!"));
        log.info("Found: {}", food.getFoodId());

        if (event.getStatus().equals(EPaymentType.CONFIRMED)) {
            food.setStatus(EPaymentType.ACCEPT);
            event.setStatus(EPaymentType.ACCEPT);
        } else if (event.getStatus().equals(EPaymentType.ROLLBACK) && !event.getSource().equals("payment")) {
            food.setStatus(EPaymentType.ROLLBACK);
            event.setStatus(EPaymentType.REJECT);
        }

        event.setFoodName(food.getFoodName());
        event.setMessage("ürün bildirimi oluşturuldu");
        String json = JsonUtil.toJson(event);

        repository.save(food);
        template.send(EventUtil.ORDERS_NOTIFICATION, event.getId(), json);
        log.info("Sent: {}", event);
    }
}