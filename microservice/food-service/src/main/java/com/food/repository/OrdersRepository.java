package com.food.repository;

import com.food.enums.EOrderType;
import com.food.model.Orders;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaSpecificationExecutor<Orders>, JpaRepository<Orders, Long>, BaseRepository<Orders, Long> {

    @Query("select COALESCE(sum(v.price),0.0)  from ORDERS v where v.food.id = :foodId and v.status = :status")
    Double getSumPrice(Long foodId, EOrderType status);

    @Query("select count(v.food.id) from ORDERS v where v.food.id = :foodId and v.status = :status")
    Integer getCountPrice(Long foodId, EOrderType status);
}
