package com.food.repository;

import com.food.model.Payment;
import com.food.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaSpecificationExecutor<Payment>, JpaRepository<Payment, Long>, BaseRepository<Payment, Long> {
    List<Payment> findPaymentByStockId(Long stockId);
}
