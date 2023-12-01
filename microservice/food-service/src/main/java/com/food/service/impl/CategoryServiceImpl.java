package com.food.service.impl;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.enums.ELogType;
import com.food.model.Category;
import com.food.repository.CategoryRepository;
import com.food.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
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
    public LoadResult getAll(DataSourceLoadOptions loadOptions) {
        loadOptions.setRequireTotalCount(true);
        var list = repository.load(loadOptions);

        log.info("Category getAll");
        return list;
    }

    @Override
    public Category create(Category dto) {
        var model = repository.save(dto);

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
