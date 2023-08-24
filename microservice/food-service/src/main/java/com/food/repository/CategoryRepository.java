package com.food.repository;

import com.food.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CategoryRepository extends JpaSpecificationExecutor<Category>, JpaRepository<Category, Long>, BaseRepository<Category, Long> {
}
