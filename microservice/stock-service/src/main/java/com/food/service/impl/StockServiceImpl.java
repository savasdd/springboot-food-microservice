package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Stock;
import com.food.repository.StockRepository;
import com.food.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class StockServiceImpl implements StockService {
    private final StockRepository repository;

    public StockServiceImpl(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        var list = repository.load(loadOptions);

        log.info("list stock {} ", list.getTotalCount());
        return list;
    }

    @Override
    @Transactional
    public List<Stock> getAll() throws GeneralException, GeneralWarning {
        var list = repository.findAll();

        log.info("list stock {} ", list.size());
        return list;
    }

    @Override
    public Stock getById(Long id) throws GeneralException, GeneralWarning {
        return repository.findById(id).get();
    }

    @Override
    @Transactional
    public Stock create(Stock dto) throws GeneralException, GeneralWarning {
        dto.setVersion(0L);
        var newModel = repository.save(dto);

        log.info("create stock {} ", dto.getId());
        return newModel;
    }

    @Override
    @Transactional
    public Stock update(Long id, Stock dto) throws GeneralException, GeneralWarning {
        var stocks = repository.findById(id);
        var newStock = stocks.map(val -> {
            val.setPrice(dto.getPrice() != null ? dto.getPrice() : val.getPrice());
            val.setAvailableItems(dto.getAvailableItems() != null ? dto.getAvailableItems() : val.getAvailableItems());
            val.setReservedItems(dto.getReservedItems() != null ? dto.getReservedItems() : val.getReservedItems());
            val.setStatus(dto.getStatus() != null ? dto.getStatus() : val.getStatus());
            val.setName(dto.getName() != null ? dto.getName() : val.getName());
            val.setUnit(dto.getUnit() != null ? dto.getUnit() : val.getUnit());
            val.setTransactionDate(dto.getTransactionDate() != null ? dto.getTransactionDate() : val.getTransactionDate());
            return val;
        });
        var model = repository.save(newStock.get());

        log.info("update stock {} ", id);
        return model;
    }

    @Override
    @Transactional
    public Stock delete(Long id) throws GeneralException, GeneralWarning {
        var model = repository.findById(id);
        if (model.isPresent()) {
            var dto = model.get();
            repository.delete(model.get());
            log.info("delete stock {} ", id);
            return dto;
        } else
            return null;
    }


}
