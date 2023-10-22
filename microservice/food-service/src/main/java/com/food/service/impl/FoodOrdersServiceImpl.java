package com.food.service.impl;

import com.food.model.Orders;
import com.food.repository.FoodRepository;
import com.food.repository.OrdersRepository;
import com.food.service.FoodFileService;
import com.food.service.FoodOrdersService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class FoodOrdersServiceImpl implements FoodOrdersService {
    private final OrdersRepository repository;
    private final FoodRepository foodRepository;
    private final FoodFileService fileService;

    public FoodOrdersServiceImpl(OrdersRepository repository, FoodRepository foodRepository, FoodFileService fileService) {
        this.repository = repository;
        this.foodRepository = foodRepository;
        this.fileService = fileService;
    }

    @Override
    public Orders getByOne(String id) {
        var model = repository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Not Found!"));
        return model;
    }

    @Override
    public LoadResult<Orders> getAll(DataSourceLoadOptions<Orders> loadOptions) {
        LoadResult<Orders> loadResult = new LoadResult<>();
        var list = repository.findAll(loadOptions.toSpecification(), loadOptions.getPageable());

        list.stream().map(val -> {
            InputStream stream = fileService.getObjects(val.getFood().getFoodId().toString());
            val.setImage(stream != null ? IOUtils.toString(stream, StandardCharsets.UTF_8) : null);
            return val;
        }).toList();

        loadResult.setData(list.getContent());
        loadResult.setTotalCount(list.stream().count());
        log.info("list órders {} ", loadResult.totalCount);
        return loadResult;
    }

    @Override
    public Orders create(Orders dto) {
        var food = foodRepository.findById(dto.getFood().getFoodId()).orElseThrow(() -> new RuntimeException("Not Found!"));
        dto.setFood(food);
        dto.setCreateDate(new Date());
        var model = repository.save(dto);

        return model;
    }

    @Override
    public Orders delete(String id) {
        var order = getByOne(id);
        repository.deleteById(UUID.fromString(id));
        return order;
    }
}
