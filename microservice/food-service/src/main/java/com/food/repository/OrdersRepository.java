package com.food.repository;

import com.food.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface OrdersRepository extends JpaSpecificationExecutor<Orders>, JpaRepository<Orders, UUID>, BaseRepository<Orders, UUID> {
}
