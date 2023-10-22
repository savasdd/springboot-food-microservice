package com.food.service.impl;

import com.food.model.LogPayment;
import com.food.repository.PaymentRepository;
import com.food.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;

    public PaymentServiceImpl(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LogPayment> getLogPayment() {
        return repository.findAll();
    }

    @Override
    public LogPayment getOneLogPayment(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

    @Override
    public LogPayment createLogPayment(LogPayment dto) {
        dto.setCreateDate(new Date());
        return repository.save(dto);
    }

    @Override
    public void deleteLogPayment(String id) {
        if (repository.existsById(id))
            repository.deleteById(id);
    }
}
