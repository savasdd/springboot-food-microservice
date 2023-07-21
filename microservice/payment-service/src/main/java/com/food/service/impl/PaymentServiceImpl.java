package com.food.service.impl;

import com.food.model.Payment;
import com.food.repository.PaymentRepository;
import com.food.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Payment> getAll() {
        log.info("GetAll payments");
        return repository.findAll();
    }

    @Override
    public Payment create(Payment dto) {
        dto.setVersion(0L);
        var model = repository.save(dto);

        log.info("create payments");
        return model;
    }

    @Override
    public Payment update(UUID id, Payment dto) {
        var payments = repository.findById(id);
        var update = payments.map(val -> {
            val.setStockId(dto.getStockId());
            val.setAmountAvailable(dto.getAmountAvailable());
            val.setAmountReserved(dto.getAmountReserved());
            val.setAmount(dto.getAmount());
            val.setTransactionDate(dto.getTransactionDate());
            val.setStatus(dto.getStatus());
            return val;
        }).get();

        var model = repository.save(update);
        log.info("update payments {}", id);
        return model;
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
        log.info("delete payments {}", id);
    }
}
