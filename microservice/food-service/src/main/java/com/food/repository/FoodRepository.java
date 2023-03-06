package com.food.repository;

import com.food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {
}
