package com.food;

import com.food.event.OrderEvent;
import com.food.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableKafkaStreams
public class FoodServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodServiceApplication.class, args);
    }

    @Bean
    public NewTopic orders() {
        return TopicBuilder.name("orders")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name("payment-orders")
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic stockTopic() {
        return TopicBuilder.name("stock-orders")
                .partitions(3)
                .compact()
                .build();
    }

    @Autowired
    private OrderServiceImpl orderService;

    @Bean
    public KStream<Long, OrderEvent> stream(StreamsBuilder builder) {
        JsonSerde<OrderEvent> orderSerde = new JsonSerde<>(OrderEvent.class);
        KStream<Long, OrderEvent> stream = builder
                .stream("payment-orders", Consumed.with(Serdes.Long(), orderSerde));

        stream.join(
                        builder.stream("stock-orders"),
                        orderService::confirm,
                        JoinWindows.of(Duration.ofSeconds(10)),
                        StreamJoined.with(Serdes.Long(), orderSerde, orderSerde))
                .peek((k, o) -> log.info("Output: {}", o))
                .to("orders");

        return stream;
    }

    @Bean
    public KTable<Long, OrderEvent> table(StreamsBuilder builder) {
        KeyValueBytesStoreSupplier store = Stores.persistentKeyValueStore("orders");
        JsonSerde<OrderEvent> orderSerde = new JsonSerde<>(OrderEvent.class);
        KStream<Long, OrderEvent> stream = builder
                .stream("orders", Consumed.with(Serdes.Long(), orderSerde));
        return stream.toTable(Materialized.<Long, OrderEvent>as(store)
                .withKeySerde(Serdes.Long())
                .withValueSerde(orderSerde));
    }

}