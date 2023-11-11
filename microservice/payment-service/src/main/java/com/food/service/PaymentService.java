package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Payment;

import java.util.List;

public interface PaymentService {

    List<Payment> getAll() throws GeneralException, GeneralWarning;

    Payment getByOne(Long id) throws GeneralException, GeneralWarning;

    LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning;

    Payment create(Payment dto) throws GeneralException, GeneralWarning;

    Payment update(Long id, Payment dto) throws GeneralException, GeneralWarning;

    void delete(Long id) throws GeneralException, GeneralWarning;

    List<Payment> getPaymentByStock(Long id) throws GeneralException, GeneralWarning;
}
