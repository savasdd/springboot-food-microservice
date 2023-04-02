package com.food.repository;

import com.food.model.LogCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<LogCategory,String> {
}
