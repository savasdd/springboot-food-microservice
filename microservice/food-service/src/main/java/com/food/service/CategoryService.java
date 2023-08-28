package com.food.service;

import com.food.model.Category;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getByOne(Long id);

    LoadResult<Category> getAll(DataSourceLoadOptions<Category> loadOptions);

    Category create(Category dto);

    Category update(Long id, Category dto);

    void delete(Long id);

}
