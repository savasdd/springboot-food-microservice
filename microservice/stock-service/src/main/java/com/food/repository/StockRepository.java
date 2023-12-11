package com.food.repository;

import com.food.model.Stock;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface StockRepository extends JpaSpecificationExecutor<Stock>, JpaRepository<Stock, Long>, BaseRepository<Stock, Long> {


}
