package com.foodtogo.mono.payment.repository;

import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.payment.core.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query("SELECT p FROM Payment p WHERE p.order.user.id = :userId")
    Page<Payment> findPaymentsByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query("SELECT p FROM Payment p WHERE p.order.restaurant.id = :restaurantId")
    Page<Payment> findPaymentsByRestaurantId(@Param("restaurantId") UUID restaurantId, Pageable pageable);

    Boolean existsByOrder(Order order);
}
