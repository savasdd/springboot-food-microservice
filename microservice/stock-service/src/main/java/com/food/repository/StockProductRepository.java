package com.food.repository;

import com.food.model.StockProduct;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface StockProductRepository extends JpaSpecificationExecutor<StockProduct>, JpaRepository<StockProduct, Long>, BaseRepository<StockProduct, Long> {

    List<StockProduct> findByFoodId(Long foodId);

}
