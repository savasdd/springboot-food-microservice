package com.food.service.impl;

import com.food.enums.ELogType;
import com.food.model.Category;
import com.food.repository.CategoryRepository;
import com.food.service.CategoryService;
import com.food.service.LogService;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;
    private final LogService logService;

    public CategoryServiceImpl(CategoryRepository repository, LogService logService) {
        this.repository = repository;
        this.logService = logService;
    }

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category getByOne(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public LoadResult<Category> getAll(DataSourceLoadOptions<Category> loadOptions) {
        LoadResult<Category> response = new LoadResult<>();
        var list = repository.findAll(loadOptions.toSpecification(), loadOptions.getPageable());
        response.setData(list.getContent());
        response.setTotalCount(list.stream().count());

        logService.eventLog("api/category", List.of(response), 200, ELogType.CATEGORY);
        log.info("Category getAll");
        return response;
    }

    @Override
    public Category create(Category dto) {
        var model = repository.save(dto);

        logService.eventLog("api/category", List.of(model), 201, ELogType.CATEGORY);
        log.info("Category save");
        return model;
    }

    @Override
    public Category update(Long id, Category dto) {
        var categors = repository.findById(id);
        var update = categors.map(val -> {
            val.setName(dto.getName() != null ? dto.getName() : val.getName());
            val.setDescription(dto.getDescription() != null ? dto.getDescription() : val.getDescription());
            val.setCategoryType(dto.getCategoryType() != null ? dto.getCategoryType() : val.getCategoryType());
            return val;
        }).get();

        var model = repository.save(update);
        logService.eventLog("api/category", List.of(model), 200, ELogType.CATEGORY);
        log.info("Category update");
        return model;
    }

    @Override
    public String delete(Long id) {
        repository.deleteById(id);

        log.info("Category delete");
        return "Delete Success";
    }
}
