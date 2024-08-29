package com.foodtogo.mono.restaurant.repository;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    // 가게 검색
    Page<Restaurant> findByRestaurantNameContaining(String restaurantName, Pageable pageable);
}
