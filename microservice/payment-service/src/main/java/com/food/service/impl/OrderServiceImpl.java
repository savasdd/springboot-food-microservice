package com.food.service.impl;

import com.food.event.OrderEvent;
import com.food.repository.PaymentRepository;
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

    private static final String SOURCE = "payment";
    private final PaymentRepository repository;
    private final KafkaTemplate<String, OrderEvent> template;

    public OrderServiceImpl(PaymentRepository repository, KafkaTemplate<String, OrderEvent> template) {
        this.repository = repository;
        this.template = template;
    }

    @Override
    public void reserve(OrderEvent order) {
        var payment = repository.findById(UUID.fromString(order.getPaymentId())).orElseThrow(() -> new RuntimeException("Not Found Payment"));
        log.info("Found: {}", payment.getPaymentId());

        if (order.getStatus().equals(EventUtil.STATUS_NEW)) {
            if (order.getAmount().intValue() < payment.getAmountAvailable().intValue()) {

                payment.setAmountReserved(payment.getAmountReserved().add(order.getAmount()));
                payment.setAmountAvailable(payment.getAmountAvailable().subtract(order.getAmount()));
                payment.setAmount(order.getAmount());
                payment.setTransactionDate(new Date());
                payment.setStatus(EventUtil.STATUS_ACCEPT);
                order.setStatus(EventUtil.STATUS_ACCEPT);
            } else {
                payment.setStatus(EventUtil.STATUS_REJECT);
                order.setStatus(EventUtil.STATUS_REJECT);
            }

            repository.save(payment);
            template.send(EventUtil.PAYMENT_ORDERS, order.getId(), order);
            log.info("Sent: {}", order);
        }
    }

    @Override
    public void confirm(OrderEvent order) {
        var payment = repository.findById(UUID.fromString(order.getPaymentId())).orElseThrow(() -> new RuntimeException("Not Found Payment"));
        log.info("Found: {}", payment.getPaymentId());

        if (order.getStatus().equals(EventUtil.STATUS_CONFIRMED)) {
            payment.setAmountReserved(payment.getAmountReserved().subtract(order.getAmount()));
            payment.setStatus(EventUtil.STATUS_CONFIRMED);
        } else if (order.getStatus().equals(EventUtil.STATUS_ROLLBACK) && !order.getSource().equals(SOURCE)) {
            payment.setAmountReserved(payment.getAmountReserved().subtract(order.getAmount()));
            payment.setAmountAvailable(payment.getAmountAvailable().add(order.getAmount()));
            payment.setStatus(EventUtil.STATUS_ROLLBACK);
        }

        repository.save(payment);
    }

}
