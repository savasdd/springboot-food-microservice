package com.food.repository;

import com.food.model.LogFood;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<LogFood,String> {
}
