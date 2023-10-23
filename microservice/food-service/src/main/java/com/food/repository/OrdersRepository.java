package com.food.repository;

import com.food.enums.EOrderType;
import com.food.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.UUID;

public interface OrdersRepository extends JpaSpecificationExecutor<Orders>, JpaRepository<Orders, UUID>, BaseRepository<Orders, UUID> {

    @Query("select sum(v.price) from ORDERS v where v.food.foodId = :foodId and v.status = :status")
    Double getSumPrice(UUID foodId, EOrderType status);

    @Query("select count(v.food.foodId) from ORDERS v where v.food.foodId = :foodId and v.status = :status")
    Integer getCountPrice(UUID foodId, EOrderType status);
}
