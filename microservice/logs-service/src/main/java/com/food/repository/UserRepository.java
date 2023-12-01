package com.food.repository;

import com.food.model.LogUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<LogUser, String> {
}
