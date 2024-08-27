package com.foodtogo.mono.order.repository;

import com.foodtogo.mono.order.core.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
