package com.food.service;

import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Payment;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;

public interface PaymentService {

    List<Payment> getAll() throws GeneralException, GeneralWarning;

    Payment getByOne(String id) throws GeneralException, GeneralWarning;

    LoadResult<Payment> getAll(DataSourceLoadOptions<Payment> loadOptions) throws GeneralException, GeneralWarning;

    Payment create(Payment dto) throws GeneralException, GeneralWarning;

    Payment update(String id, Payment dto) throws GeneralException, GeneralWarning;

    void delete(String id) throws GeneralException, GeneralWarning;

    List<Payment> getPaymentByStock(String id) throws GeneralException, GeneralWarning;
}
