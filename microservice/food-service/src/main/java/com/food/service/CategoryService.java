package com.food.service;

import com.food.model.Category;
import com.food.spesification.response.LoadResult;
import com.food.spesification.source.DataSourceLoadOptions;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

public interface CategoryService {

    List<Category> getAll();

    Category getByOne(Long id);

    LoadResult<Category> getAll(DataSourceLoadOptions<Category> loadOptions);

    Category create(Category dto);

    Category update(Long id, Category dto);

    String delete(Long id);

}
