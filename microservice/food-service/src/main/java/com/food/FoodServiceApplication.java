package com.food;

import com.food.db.CreatingDatabase;
import com.food.event.OrderEvent;
import com.food.service.OrderService;
import com.food.utils.EventUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;
import java.util.concurrent.Executor;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@EnableKafkaStreams
@EnableAsync
public class FoodServiceApplication {
    public static void main(String[] args) {
        CreatingDatabase.builder().build();
        SpringApplication.run(FoodServiceApplication.class, args);
    }

    @Autowired
    private OrderService orderService;

    @Bean
    public KStream<Long, OrderEvent> stream(StreamsBuilder builder) {
        JsonSerde<OrderEvent> orderSerde = new JsonSerde<>(OrderEvent.class);
        KStream<Long, OrderEvent> stream = builder.stream(EventUtil.ORDERS_PAYMENT, Consumed.with(Serdes.Long(), orderSerde));

        stream.join(builder.stream(EventUtil.ORDERS_STOCK), orderService::confirm, JoinWindows.of(Duration.ofSeconds(10)),
                StreamJoined.with(Serdes.Long(), orderSerde, orderSerde)).peek((k, o) -> log.info("Output: {}", o)).to(EventUtil.ORDERS);

        return stream;
    }

    @Bean
    public KTable<Long, OrderEvent> table(StreamsBuilder builder) {
        KeyValueBytesStoreSupplier store = Stores.persistentKeyValueStore(EventUtil.ORDERS);
        JsonSerde<OrderEvent> orderSerde = new JsonSerde<>(OrderEvent.class);
        KStream<Long, OrderEvent> stream = builder.stream(EventUtil.ORDERS, Consumed.with(Serdes.Long(), orderSerde));

        return stream.toTable(Materialized.<Long, OrderEvent>as(store).withKeySerde(Serdes.Long()).withValueSerde(orderSerde));
    }

    @Bean
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("kafkaSender-");
        executor.initialize();
        return executor;
    }

}