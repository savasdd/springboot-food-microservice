package com.food.repository;

import com.food.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface FoodRepository extends JpaSpecificationExecutor<Food>, JpaRepository<Food, UUID>, BaseRepository<Food, UUID> {
}
