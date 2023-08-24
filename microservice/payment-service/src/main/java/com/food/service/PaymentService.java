package com.food.service;

import com.food.model.Payment;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;
import java.util.UUID;

public interface PaymentService {

    List<Payment> getAll();

    LoadResult<Payment> getAll(DataSourceLoadOptions<Payment> loadOptions);

    Payment create(Payment dto);

    Payment update(UUID id, Payment dto);

    void delete(UUID id);
}
