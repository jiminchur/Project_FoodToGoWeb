package com.foodtogo.mono.order.repository;

import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.order.core.domain.OrderFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderFoodRepository extends JpaRepository<OrderFood, UUID> {
    List<OrderFood> findByOrder(Order order);
}
