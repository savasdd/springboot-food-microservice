package com.food.repository;

import com.food.model.Food;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FoodRepository extends JpaSpecificationExecutor<Food>, JpaRepository<Food, UUID>, BaseRepository<Food, UUID> {
}
