package com.food.service.impl;

import com.food.aop.MongoLog;
import com.food.dto.StockDto;
import com.food.model.Stock;
import com.food.repository.StockRepository;
import com.food.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StockServiceImpl implements StockService {
    private final StockRepository repository;

    public StockServiceImpl(StockRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<StockDto> getAll() {
        var list = repository.findAll();
        var dtolList = list.stream().map(val -> modelMapDto(val)).collect(Collectors.toList());

        log.info("list stock {} ", list.size());
        return dtolList;
    }

    @MongoLog(status = 201)
    @Override
    @Transactional
    public StockDto create(StockDto dto) {
        var model = dtoMapModel(dto);
        model.setVersion(0L);
        model.setFoodId(dto.getFoodId());
        var newModel = repository.save(model);

        log.info("create stock {} ", newModel.getStockId());
        return modelMapDto(newModel);
    }

    @MongoLog(status = 200)
    @Override
    @Transactional
    public StockDto update(UUID id, StockDto dto) {
        var stocks = repository.findById(id);
        var newStock = stocks.map(val -> {
            val.setFoodId(dto.getFoodId());
            val.setPrice(dto.getPrice());
            val.setDescription(dto.getDescription());
            return val;
        });
        var model = repository.save(newStock.get());

        log.info("update stock {} ", id);
        return modelMapDto(model);
    }

    @MongoLog(status = 202)
    @Override
    @Transactional
    public StockDto delete(UUID id) {
        var model = repository.findById(id);
        if (model.isPresent()) {
            var dto = modelMapDto(model.get());
            repository.delete(model.get());
            log.info("delete stock {} ", id);
            return dto;
        } else
            return null;
    }


    private StockDto modelMapDto(Stock dto) {
        return StockDto.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).price(dto.getPrice()).description(dto.getDescription()).build();
    }

    private Stock dtoMapModel(StockDto dto) {
        return Stock.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).price(dto.getPrice()).description(dto.getDescription()).build();
    }
}
