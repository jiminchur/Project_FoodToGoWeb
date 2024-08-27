package com.foodtogo.mono.order.repository;

import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.user.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByRestaurant(Restaurant restaurant);

    List<Order> findByUser(User user);
}
