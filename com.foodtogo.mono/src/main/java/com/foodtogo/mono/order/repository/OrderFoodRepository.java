package com.foodtogo.mono.order.repository;

import com.foodtogo.mono.order.core.domain.OrderFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderFoodRepository extends JpaRepository<OrderFood, UUID> {
}
