package com.food.repository;

import com.food.model.LogStock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<LogStock,String> {
}
