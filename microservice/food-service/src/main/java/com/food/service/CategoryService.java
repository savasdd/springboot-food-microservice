package com.food.service;

import com.food.data.options.DataSourceLoadOptions;
import com.food.data.response.LoadResult;
import com.food.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getByOne(Long id);

    LoadResult getAll(DataSourceLoadOptions loadOptions);

    Category create(Category dto);

    Category update(Long id, Category dto);

    String delete(Long id);

}
