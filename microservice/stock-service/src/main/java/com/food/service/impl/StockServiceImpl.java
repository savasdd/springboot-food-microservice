package com.food.service.impl;

import com.food.aop.MongoLog;
import com.food.dto.StockDto;
import com.food.enums.ELogType;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Stock;
import com.food.repository.StockRepository;
import com.food.service.LogService;
import com.food.service.StockService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
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
    private final LogService logService;

    public StockServiceImpl(StockRepository repository, LogService logService) {
        this.repository = repository;
        this.logService = logService;
    }

    @Override
    public LoadResult<Stock> getAll(DataSourceLoadOptions<Stock> loadOptions) throws GeneralException, GeneralWarning {
        LoadResult<Stock> response = new LoadResult<>();
        var list = repository.findAll(loadOptions.toSpecification(), loadOptions.getPageable());
        response.setData(list.getContent());
        response.setTotalCount(list.stream().count());

        logService.eventLog("api/stock", List.of(response), 200, ELogType.STOCK);
        log.info("list stock {} ", response.totalCount);
        return response;
    }

    @Override
    @Transactional
    public List<StockDto> getAll() throws GeneralException, GeneralWarning {
        var list = repository.findAll();
        var dtolList = list.stream().map(val -> modelMapDto(val)).collect(Collectors.toList());

        log.info("list stock {} ", list.size());
        return dtolList;
    }

    @Override
    public StockDto getById(String id) throws GeneralException, GeneralWarning {
        return modelMapDto(repository.findById(UUID.fromString(id)).get());
    }

    @MongoLog(status = 201)
    @Override
    @Transactional
    public StockDto create(StockDto dto) throws GeneralException, GeneralWarning {
        var model = dtoMapModel(dto);
        model.setVersion(0L);
        model.setFoodId(dto.getFoodId());
        var newModel = repository.save(model);

        logService.eventLog("api/stock", List.of(model), 201, ELogType.STOCK);
        log.info("create stock {} ", newModel.getStockId());
        return modelMapDto(newModel);
    }

    @MongoLog(status = 200)
    @Override
    @Transactional
    public StockDto update(String id, StockDto dto) throws GeneralException, GeneralWarning {
        var stocks = repository.findById(UUID.fromString(id));
        var newStock = stocks.map(val -> {
            val.setFoodId(dto.getFoodId() != null ? dto.getFoodId() : val.getFoodId());
            val.setPrice(dto.getPrice() != null ? dto.getPrice() : val.getPrice());
            val.setAvailableItems(dto.getAvailableItems() != null ? dto.getAvailableItems() : val.getAvailableItems());
            val.setReservedItems(dto.getReservedItems() != null ? dto.getReservedItems() : val.getReservedItems());
            val.setStatus(dto.getStatus() != null ? dto.getStatus() : val.getStatus());
            val.setName(dto.getName() != null ? dto.getName() : val.getName());
            val.setDescription(dto.getDescription() != null ? dto.getDescription() : val.getDescription());
            val.setTransactionDate(dto.getTransactionDate() != null ? dto.getTransactionDate() : val.getTransactionDate());
            return val;
        });
        var model = repository.save(newStock.get());

        logService.eventLog("api/stock", List.of(model), 200, ELogType.STOCK);
        log.info("update stock {} ", id);
        return modelMapDto(model);
    }

    @MongoLog(status = 202)
    @Override
    @Transactional
    public StockDto delete(String id) throws GeneralException, GeneralWarning {
        var model = repository.findById(UUID.fromString(id));
        if (model.isPresent()) {
            var dto = modelMapDto(model.get());
            repository.delete(model.get());
            log.info("delete stock {} ", id);
            return dto;
        } else
            return null;
    }

    @Override
    public List<Stock> getStockByFoodId(String id) throws GeneralException, GeneralWarning {
        return repository.findByFoodId(UUID.fromString(id));
    }


    private StockDto modelMapDto(Stock dto) {
        return StockDto.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).name(dto.getName()).price(dto.getPrice()).availableItems(dto.getAvailableItems()).reservedItems(dto.getReservedItems()).status(dto.getStatus()).transactionDate(dto.getTransactionDate()).description(dto.getDescription()).build();
    }

    private Stock dtoMapModel(StockDto dto) {
        return Stock.builder().stockId(dto.getStockId()).foodId(dto.getFoodId()).name(dto.getName()).price(dto.getPrice()).availableItems(dto.getAvailableItems()).reservedItems(dto.getReservedItems()).status(dto.getStatus()).transactionDate(dto.getTransactionDate()).description(dto.getDescription()).build();
    }
}
