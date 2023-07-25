package com.food;

import com.food.event.OrderEvent;
import com.food.service.OrderService;
import com.food.utils.EventUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
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
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

import java.time.Duration;

@Slf4j
@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@EnableKafkaStreams
@EnableAsync
public class FoodServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodServiceApplication.class, args);
    }

    @Bean
    public NewTopic orders() {
        return TopicBuilder.name(EventUtil.ORDERS)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name(EventUtil.PAYMENT_ORDERS)
                .partitions(3)
                .compact()
                .build();
    }

    @Bean
    public NewTopic stockTopic() {
        return TopicBuilder.name(EventUtil.STOCK_ORDERS)
                .partitions(3)
                .compact()
                .build();
    }

    @Autowired
    private OrderService orderService;

    @Bean
    public KStream<Long, OrderEvent> stream(StreamsBuilder builder) {
        JsonSerde<OrderEvent> orderSerde = new JsonSerde<>(OrderEvent.class);
        KStream<Long, OrderEvent> stream = builder.stream(EventUtil.PAYMENT_ORDERS, Consumed.with(Serdes.Long(), orderSerde));

        stream.join(builder.stream(EventUtil.STOCK_ORDERS), orderService::confirm,
                        JoinWindows.of(Duration.ofSeconds(10)),
                        StreamJoined.with(Serdes.Long(), orderSerde, orderSerde))
                .peek((k, o) -> log.info("Output: {}", o))
                .to(EventUtil.ORDERS);

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