package com.food.repository;

import com.food.model.LogPayment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<LogPayment,String> {
}
