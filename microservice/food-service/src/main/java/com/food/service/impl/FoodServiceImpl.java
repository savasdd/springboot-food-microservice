package com.food.service.impl;

import com.food.aop.MongoLog;
import com.food.dto.CategoryDto;
import com.food.dto.FoodDto;
import com.food.event.StockEvent;
import com.food.model.Category;
import com.food.model.Food;
import com.food.repository.CategoryRepository;
import com.food.repository.FoodRepository;
import com.food.service.FoodService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FoodServiceImpl implements FoodService {
    private final KafkaTemplate<String, StockEvent> kafkaTemplateStock;
    private final FoodRepository repository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public FoodServiceImpl(KafkaTemplate<String, StockEvent> kafkaTemplateStock, FoodRepository repository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.kafkaTemplateStock = kafkaTemplateStock;
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
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
    public FoodDto getByOne(String id) {
        var model = repository.findById(UUID.fromString(id));
        return modelMapDto(model.get());
    }

    @Override
    public LoadResult<Food> getAll(DataSourceLoadOptions<Food> loadOptions) {
        LoadResult<Food> loadResult = new LoadResult<>();
        var list = repository.findAll(loadOptions.toSpecification(), loadOptions.getPageable());
        loadResult.setData(list.getContent());
        loadResult.setTotalCount(list.stream().count());

        return loadResult;
    }

    @MongoLog(status = 201)
    @Override
    @Transactional
    public FoodDto create(FoodDto dto) {
        var category = categoryRepository.findById(dto.getCategory().getId() != null ? dto.getCategory().getId() : -1);
        var model = dtoMapModel(dto);
        model.setCategory(category.isPresent() ? category.get() : null);
        model.setVersion(0L);
        var newModel = repository.save(model);
        log.info("create food {} ", newModel.getFoodId());
        return modelMapDto(newModel);
    }

    @MongoLog(status = 200)
    @Override
    @Transactional
    public FoodDto update(UUID id, FoodDto dto) {
        var category = categoryRepository.findById(dto.getCategory().getId() != null ? dto.getCategory().getId() : -1);
        var foods = repository.findById(id);
        var newFood = foods.map(var -> {
            var.setFoodName(dto.getFoodName());
            var.setCategory(category.isPresent() ? category.get() : var.getCategory());
            var.setDescription(dto.getDescription());
            return var;
        });
        var newModel = repository.save(newFood.get());
        log.info("update food {} ", id);

        return modelMapDto(newModel);
    }

    @MongoLog(status = 202)
    @Override
    @Transactional
    public FoodDto delete(UUID id) {
        var food = repository.findById(id);
        if (food.isPresent()) {
            var dto = modelMapDto(food.get());
            repository.delete(food.get());
            return dto;
        } else
            return null;
    }


    private Food dtoMapModel(FoodDto dto) {
        return Food.builder().foodId(dto.getFoodId()).foodName(dto.getFoodName()).category(modelMapper.map(dto.getCategory(), Category.class)).description(dto.getDescription()).build();
    }

    private FoodDto modelMapDto(Food dto) {
        return FoodDto.builder().foodId(dto.getFoodId()).foodName(dto.getFoodName()).category(modelMapper.map(dto.getCategory(), CategoryDto.class)).description(dto.getDescription()).build();
    }

}
