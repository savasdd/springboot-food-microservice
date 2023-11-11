package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.enums.ELogType;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Payment;
import com.food.repository.PaymentRepository;
import com.food.service.LogService;
import com.food.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository repository;
    private final LogService logService;

    public PaymentServiceImpl(PaymentRepository repository, LogService logService) {
        this.repository = repository;
        this.logService = logService;
    }

    @Override
    public List<Payment> getAll() throws GeneralException, GeneralWarning {
        return repository.findAll();
    }

    @Override
    public Payment getByOne(Long id) throws GeneralException, GeneralWarning {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

    @Override
    public LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        var list = repository.load(loadOptions);

        logService.eventLog("api/payment", List.of(list), 200, ELogType.PAYMENT);
        return list;
    }

    @Override
    public Payment create(Payment dto) throws GeneralException, GeneralWarning {
        dto.setVersion(0L);
        var model = repository.save(dto);

        logService.eventLog("api/payment", List.of(model), 201, ELogType.PAYMENT);
        log.info("create payments");
        return model;
    }

    @Override
    public Payment update(Long id, Payment dto) throws GeneralException, GeneralWarning {
        var payments = repository.findById(id);
        var update = payments.map(val -> {
            val.setStockId(dto.getStockId() != null ? dto.getStockId() : val.getStockId());
            val.setAmountAvailable(dto.getAmountAvailable() != null ? dto.getAmountAvailable() : val.getAmountAvailable());
            val.setAmountReserved(dto.getAmountReserved() != null ? dto.getAmountReserved() : val.getAmountReserved());
            val.setAmount(dto.getAmount() != null ? dto.getAmount() : val.getAmount());
            val.setTransactionDate(dto.getTransactionDate() != null ? dto.getTransactionDate() : val.getTransactionDate());
            val.setStatus(dto.getStatus() != null ? dto.getStatus() : val.getStatus());
            return val;
        }).get();

        var model = repository.save(update);
        logService.eventLog("api/payment", List.of(model), 200, ELogType.PAYMENT);
        log.info("update payments {}", id);
        return model;
    }

    @Override
    public void delete(Long id) throws GeneralException, GeneralWarning {
        repository.deleteById(id);
        log.info("delete payments {}", id);
    }

    @Override
    public List<Payment> getPaymentByStock(Long id) {
        return repository.findPaymentByStockId(id);
    }
}
