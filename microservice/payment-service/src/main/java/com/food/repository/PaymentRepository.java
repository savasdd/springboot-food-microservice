package com.food.repository;

import com.food.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaSpecificationExecutor<Payment>, JpaRepository<Payment, UUID>, BaseRepository<Payment, UUID> {

    List<Payment> findPaymentByStockId(UUID stockId);
}
