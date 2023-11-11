package com.food.service.impl;

import com.food.enums.EPaymentType;
import com.food.event.OrderEvent;
import com.food.repository.PaymentRepository;
import com.food.service.OrderService;
import com.food.utils.EventUtil;
import com.food.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private static final String SOURCE = "payment";
    private final PaymentRepository repository;
    private final KafkaTemplate<String, String> template;

    public OrderServiceImpl(PaymentRepository repository, KafkaTemplate<String, String> template) {
        this.repository = repository;
        this.template = template;
    }

    @Override
    public void reserve(OrderEvent order) {
        var payment = repository.findById(Long.parseLong(order.getPaymentId())).orElseThrow(() -> new RuntimeException("Not Found Payment"));
        log.info("Found: {}", payment.getId());

        if (order.getStatus().equals(EPaymentType.NEW)) {
            if (order.getAmount().intValue() < payment.getAmountAvailable().intValue()) {

                payment.setAmountReserved(payment.getAmountReserved().add(order.getAmount()));
                payment.setAmountAvailable(payment.getAmountAvailable().subtract(order.getAmount()));
                payment.setAmount(order.getAmount());
                payment.setTransactionDate(new Date());
                payment.setStatus(EPaymentType.ACCEPT);
                order.setStatus(EPaymentType.ACCEPT);
            } else {
                payment.setStatus(EPaymentType.REJECT);
                order.setStatus(EPaymentType.REJECT);
            }

            order.setMessage("ürün ödeme kontrolu yapıldı");
            String json = JsonUtil.toJson(order);
            repository.save(payment);
            template.send(EventUtil.ORDERS_PAYMENT, order.getId(), json);
            log.info("Sent: {}", order);
        }
    }

    @Override
    public void confirm(OrderEvent order) {
        var payment = repository.findById(Long.parseLong(order.getPaymentId())).orElseThrow(() -> new RuntimeException("Not Found Payment"));
        log.info("Found: {}", payment.getId());

        if (order.getStatus().equals(EPaymentType.ACCEPT)) {
            payment.setAmountReserved(payment.getAmountReserved() != null ? payment.getAmountReserved().add(order.getAmount()) : BigDecimal.ZERO);
            payment.setAmountAvailable(payment.getAmountAvailable().subtract(order.getAmount()));
            payment.setAmount(payment.getAmount() != null ? payment.getAmount().add(order.getAmount()) : BigDecimal.ZERO);
            payment.setStatus(EPaymentType.CONFIRMED);
            order.setStatus(EPaymentType.CONFIRMED);
        } else if (order.getStatus().equals(EPaymentType.ROLLBACK) && !order.getSource().equals(SOURCE)) {
            payment.setAmountReserved(payment.getAmountReserved().subtract(order.getAmount()));
            payment.setAmountAvailable(payment.getAmountAvailable().add(order.getAmount()));
            payment.setAmount(payment.getAmount().subtract(order.getAmount()));
            payment.setStatus(EPaymentType.ROLLBACK);
            order.setStatus(EPaymentType.REJECT);
        }

        order.setMessage("ürün ödeme kontrolu yapıldı");
        String json = JsonUtil.toJson(order);
        repository.save(payment);
        template.send(EventUtil.ORDERS_PAYMENT, order.getId(), json);
        log.info("Sent: {}", order);
    }

}
