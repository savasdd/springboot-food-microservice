package com.food.service.impl;

import com.food.model.LogStock;
import com.food.repository.StockRepository;
import com.food.service.StockService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    public StockServiceImpl(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LogStock> getLogStock() {
        return repository.findAll();
    }

    @Override
    public LogStock getOneLogStock(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

    @Override
    public LogStock createLogStock(LogStock dto) {
        dto.setCreateDate(new Date());
        return repository.save(dto);
    }

    @Override
    public void deleteLogStock(String id) {
        if (repository.existsById(id))
            repository.deleteById(id);
    }
}
