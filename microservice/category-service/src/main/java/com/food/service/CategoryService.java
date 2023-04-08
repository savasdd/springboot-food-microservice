package com.food.service;

import com.food.dto.CategoryDto;
import com.food.service.impl.CategoryServiceImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    public List<CategoryDto> getAll();

    public CategoryDto create(CategoryDto dto);

    public CategoryDto update(UUID id, CategoryDto dto);

    public CategoryDto delete(UUID id);

}
