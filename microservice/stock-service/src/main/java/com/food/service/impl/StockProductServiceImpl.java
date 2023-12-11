package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Stock;
import com.food.model.StockProduct;
import com.food.repository.StockProductRepository;
import com.food.repository.StockRepository;
import com.food.service.StockProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class StockProductServiceImpl implements StockProductService {
    private final StockRepository stockRepository;
    private final StockProductRepository repository;

    public StockProductServiceImpl(StockRepository stockRepository, StockProductRepository repository) {
        this.stockRepository = stockRepository;
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
    public List<StockProduct> getAll() throws GeneralException, GeneralWarning {
        var list = repository.findAll();

        log.info("list stock {} ", list.size());
        return list;
    }

    @Override
    public StockProduct getById(Long id) throws GeneralException, GeneralWarning {
        return repository.findById(id).get();
    }

    @Override
    @Transactional
    public StockProduct create(StockProduct dto) throws GeneralException, GeneralWarning {
        var stock = stockRepository.findById(dto.getStock() != null ? dto.getStock().getId() : -1);
        dto.setStock(stock.isPresent() ? stock.get() : null);
        dto.setVersion(0L);
        var newModel = repository.save(dto);

        log.info("create stock {} ", dto.getId());
        return newModel;
    }

    @Override
    @Transactional
    public StockProduct update(Long id, StockProduct dto) throws GeneralException, GeneralWarning {
        var stock = stockRepository.findById(dto.getStock() != null ? dto.getStock().getId() : -1);

        var stocks = repository.findById(id);
        var newStock = stocks.map(val -> {
            val.setStock(stock.isPresent() ? stock.get() : val.getStock());
            val.setFoodId(dto.getFoodId() != null ? dto.getFoodId() : val.getFoodId());
            val.setDescription(dto.getDescription() != null ? dto.getDescription() : val.getDescription());
            return val;
        });
        var model = repository.save(newStock.get());

        log.info("update stock {} ", id);
        return model;
    }

    @Override
    @Transactional
    public StockProduct delete(Long id) throws GeneralException, GeneralWarning {
        var model = repository.findById(id);
        if (model.isPresent()) {
            var dto = model.get();
            repository.delete(model.get());
            log.info("delete stock {} ", id);
            return dto;
        } else
            return null;
    }

    @Override
    public List<StockProduct> getByFoodId(Long id) {
        return repository.findByFoodId(id);
    }


}
