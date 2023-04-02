package com.food.repository;

import com.food.model.LogAccount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRepository extends MongoRepository<LogAccount,String> {
}
