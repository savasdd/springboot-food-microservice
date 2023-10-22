package com.food.service.impl;

import com.food.model.LogCategory;
import com.food.repository.CategoryRepository;
import com.food.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LogCategory> getLogCategory() {
        return repository.findAll();
    }

    @Override
    public LogCategory getOneLogCategory(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found!"));
    }

    @Override
    public LogCategory createLogCategory(LogCategory dto) {
        dto.setCreateDate(new Date());
        return repository.save(dto);
    }

    @Override
    public void deleteLogCategory(String id) {
        if (repository.existsById(id))
            repository.deleteById(id);
    }
}
