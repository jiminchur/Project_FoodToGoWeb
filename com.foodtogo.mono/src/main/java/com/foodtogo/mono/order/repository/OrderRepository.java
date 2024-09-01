package com.foodtogo.mono.order.repository;

import com.foodtogo.mono.order.core.domain.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT p FROM Order p WHERE p.restaurant.id = :restaurantId")
    Page<Order> findByRestaurantId(UUID restaurantId, Pageable pageable);

    @Query("SELECT p FROM Order p WHERE p.user.id = :userId")
    Page<Order> findByUserId(UUID userId, Pageable pageable);
}
