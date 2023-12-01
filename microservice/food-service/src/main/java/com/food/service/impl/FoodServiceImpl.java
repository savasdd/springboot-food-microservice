package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.dto.CategoryDto;
import com.food.dto.FoodDto;
import com.food.enums.ELogType;
import com.food.exception.GeneralException;
import com.food.exception.GeneralWarning;
import com.food.model.Food;
import com.food.repository.CategoryRepository;
import com.food.repository.FoodRepository;
import com.food.service.FoodFileService;
import com.food.service.FoodService;
import com.food.service.LogService;
import com.food.utils.JsonUtil;
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
    public Food getByOne(Long id) throws GeneralException, GeneralWarning {
        var model = repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));

        return model;
    }

    @Override
    public LoadResult getAll(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        loadOptions.setRequireTotalCount(true);
        var list = repository.load(loadOptions);

//        logService.eventLog("api/foods", List.of(list), 200, ELogType.FOOD);
        log.info("list food {} ", list.getTotalCount());
        return list;
    }

    @Override
    public List<FoodDto> getAll() throws GeneralException, GeneralWarning {
        var list = repository.findAll();
        var dtoList = list.stream().map(var -> modelMapDto(var)).collect(Collectors.toList());
        log.info("list food {} ", list.size());
        return dtoList;
    }


    @Override
    public LoadResult getAllOrder(DataSourceLoadOptions loadOptions) throws GeneralException, GeneralWarning {
        loadOptions.setRequireTotalCount(true);
        var result = repository.load(loadOptions);
        var list = JsonUtil.fromJsonList(result.getItems(), Food.class);

        list.stream().map(val -> {
            InputStream stream = fileService.getObjects(val.getId().toString());
            val.setImage(stream != null ? IOUtils.toString(stream, StandardCharsets.UTF_8) : null);
            return val;
        }).toList();

        LoadResult loadResult = new LoadResult();
        loadResult.setItems(list);
        loadResult.setTotalCount(list.stream().count());

        log.info("list food {} ", result.getTotalCount());
        return loadResult;
    }

    @Override
    @Transactional
    public Food create(Food dto) throws GeneralException, GeneralWarning {
        var category = categoryRepository.findById((dto.getCategory() != null && dto.getCategory().getId() != null) ? dto.getCategory().getId() : -1);
        dto.setCategory(category.isPresent() ? category.get() : null);
        dto.setVersion(0L);
        var newModel = repository.save(dto);

        logService.eventLog("api/foods", List.of(newModel), 201, ELogType.FOOD);
        log.info("create food {} ", newModel.getId());
        return newModel;
    }

    @Override
    @Transactional
    public Food update(Long id, Food dto) throws GeneralException, GeneralWarning {
        var category = categoryRepository.findById((dto.getCategory() != null && dto.getCategory().getId() != null) ? dto.getCategory().getId() : -1);
        var foods = repository.findById(id);
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
        return newModel;
    }

    @Override
    @Transactional
    public Food delete(Long id) throws GeneralException, GeneralWarning {
        var food = repository.findById(id);
        if (food.isPresent()) {
            var dto = food.get();
            repository.delete(food.get());
            logService.eventLog("api/foods", List.of(dto), 200, ELogType.FOOD);
            return dto;
        } else
            return null;
    }


    private FoodDto modelMapDto(Food dto) {
        modelMapper.getConfiguration().setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setMatchingStrategy(MatchingStrategies.LOOSE)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setSkipNullEnabled(true);
        return FoodDto.builder().foodId(dto.getId()).foodName(dto.getFoodName()).price(dto.getPrice()).category(dto.getCategory() != null ?
                modelMapper.map(dto.getCategory(), CategoryDto.class) : null).description(dto.getDescription()).build();
    }

}
