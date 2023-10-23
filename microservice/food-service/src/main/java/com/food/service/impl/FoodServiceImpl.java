package com.food.service.impl;

import com.food.aop.MongoLog;
import com.food.dto.CategoryDto;
import com.food.dto.FoodDto;
import com.food.enums.ELogType;
import com.food.model.Category;
import com.food.model.Food;
import com.food.repository.CategoryRepository;
import com.food.repository.FoodRepository;
import com.food.service.FoodFileService;
import com.food.service.FoodService;
import com.food.service.LogService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodServiceImpl implements FoodService {
    private final FoodRepository repository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final FoodFileService fileService;
    private final LogService logService;

    public FoodServiceImpl(FoodRepository repository, CategoryRepository categoryRepository, ModelMapper modelMapper, FoodFileService fileService, LogService logService) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.logService = logService;
    }

    @Override
    @Transactional
    public List<FoodDto> getAll() {
        var list = repository.findAll();
        var dtoList = list.stream().map(var -> modelMapDto(var)).collect(Collectors.toList());
        log.info("list food {} ", list.size());
        return dtoList;
    }

    @Override
    public Food getByOne(String id) {
        var model = repository.findById(UUID.fromString(id)).orElseThrow(() -> new RuntimeException("Not Found!"));
        return model;
    }

    @Override
    public LoadResult<Food> getAll(DataSourceLoadOptions<Food> loadOptions) {
        LoadResult<Food> loadResult = new LoadResult<>();
        var list = repository.findAll(loadOptions.toSpecification(), loadOptions.getPageable());

        loadResult.setData(list.getContent());
        loadResult.setTotalCount(list.stream().count());
        logService.eventLog("api/foods", List.of(loadResult), 200, ELogType.FOOD);
        log.info("list food {} ", loadResult.totalCount);
        return loadResult;
    }

    @Override
    public LoadResult<Food> getAllOrder(DataSourceLoadOptions<Food> loadOptions) {
        LoadResult<Food> loadResult = new LoadResult<>();
        var list = repository.findAll(loadOptions.toSpecification(), loadOptions.getPageable());

        list.stream().map(val -> {
            InputStream stream = fileService.getObjects(val.getFoodId().toString());
            val.setImage(stream != null ? IOUtils.toString(stream, StandardCharsets.UTF_8) : null);
            return val;
        }).toList();

        loadResult.setData(list.getContent());
        loadResult.setTotalCount(list.stream().count());
        log.info("list food {} ", loadResult.totalCount);
        return loadResult;
    }

    @Override
    @Transactional
    public Food create(Food dto) {
        var category = categoryRepository.findById((dto.getCategory() != null && dto.getCategory().getId() != null) ? dto.getCategory().getId() : -1);
        dto.setCategory(category.isPresent() ? category.get() : null);
        dto.setVersion(0L);
        var newModel = repository.save(dto);

        logService.eventLog("api/foods", List.of(newModel), 201, ELogType.FOOD);
        log.info("create food {} ", newModel.getFoodId());
        return newModel;
    }

    @Override
    @Transactional
    public FoodDto update(String id, Food dto) {
        var category = categoryRepository.findById((dto.getCategory() != null && dto.getCategory().getId() != null) ? dto.getCategory().getId() : -1);
        var foods = repository.findById(UUID.fromString(id));
        var newFood = foods.map(var -> {
            var.setFoodName(dto.getFoodName() != null ? dto.getFoodName() : var.getFoodName());
            var.setPrice(dto.getPrice() != null ? dto.getPrice() : var.getPrice());
            var.setCategory(category.isPresent() ? category.get() : var.getCategory());
            var.setDescription(dto.getDescription() != null ? dto.getDescription() : var.getDescription());
            var.setStatus(dto.getStatus() != null ? dto.getStatus() : var.getStatus());
            return var;
        }).get();

        var newModel = repository.save(newFood);
        logService.eventLog("api/foods", List.of(newModel), 200, ELogType.FOOD);
        log.info("update food {} ", id);
        return modelMapDto(newModel);
    }

    @Override
    @Transactional
    public Food delete(String id) {
        var food = repository.findById(UUID.fromString(id));
        if (food.isPresent()) {
            var dto = food.get();
            repository.delete(food.get());
            logService.eventLog("api/foods", List.of(dto), 200, ELogType.FOOD);
            return dto;
        } else
            return null;
    }


    private Food dtoMapModel(FoodDto dto) {
        return Food.builder().foodId(dto.getFoodId()).foodName(dto.getFoodName()).category(dto.getCategory() != null
                ? modelMapper.map(dto.getCategory(), Category.class) : null).description(dto.getDescription()).build();
    }

    private FoodDto modelMapDto(Food dto) {
        modelMapper.getConfiguration().setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);
        return FoodDto.builder().foodId(dto.getFoodId()).foodName(dto.getFoodName()).price(dto.getPrice()).category(dto.getCategory() != null ?
                modelMapper.map(dto.getCategory(), CategoryDto.class) : null).description(dto.getDescription()).build();
    }

}
