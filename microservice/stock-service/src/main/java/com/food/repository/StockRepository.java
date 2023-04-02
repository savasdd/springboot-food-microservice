package com.food.repository;

import com.food.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaRepository<Stock, UUID> {

    List<Stock> findByFoodId(UUID foodId);
    Optional<Stock> findByFoodIdAndStockId(UUID foodId, UUID stockId);
}
