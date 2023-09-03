package com.food.service;

import com.food.model.Payment;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;

public interface PaymentService {

    List<Payment> getAll();

    Payment getByOne(String id);

    LoadResult<Payment> getAll(DataSourceLoadOptions<Payment> loadOptions);

    Payment create(Payment dto);

    Payment update(String id, Payment dto);

    void delete(String id);
}
