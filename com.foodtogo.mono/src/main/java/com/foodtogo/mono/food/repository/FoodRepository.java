package com.foodtogo.mono.food.repository;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {

    // 가게에 속한 음식 조회
    Page<Food> findByRestaurant(Restaurant restaurant, Pageable pageable);

    // User의 대한 가게에 속한 음식 조회
    Page<Food> findByRestaurantAndIsHiddenFalseAndDeletedAtIsNull(Restaurant restaurants, Pageable pageable);
}
