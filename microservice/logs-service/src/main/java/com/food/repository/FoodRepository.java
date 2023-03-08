package com.food.repository;

import com.food.model.Food;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FoodRepository extends MongoRepository<Food,String> {
}
