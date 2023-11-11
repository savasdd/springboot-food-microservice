package com.food.repository;

import com.food.model.Stock;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository extends JpaSpecificationExecutor<Stock>, JpaRepository<Stock, Long>, BaseRepository<Stock, Long> {

    List<Stock> findByFoodId(Long foodId);

}
