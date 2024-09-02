package com.foodtogo.mono.food.repository;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {

    // 가게에 속한 음식 조회
    @Query("SELECT r.foodInfoTitle,r.foodInfoDesc,r.foodInfoPrice,r.isSale FROM Food r WHERE r.restaurant = ?1 AND r.deletedAt IS NULL")
    Page<Food> findByRestaurant(Restaurant restaurant, Pageable pageable);

    // User의 대한 가게에 속한 음식 조회
    @Query("SELECT r.foodInfoTitle,r.foodInfoDesc,r.foodInfoPrice,r.isSale FROM Food r WHERE r.restaurant = ?1 AND r.isHidden = false AND r.deletedAt IS NULL")
    Page<Food> findByRestaurantAndIsHiddenFalseAndDeletedAtIsNull(Restaurant restaurants, Pageable pageable);

    // 음식 이름으로 검색
    @Query("SELECT r.foodInfoTitle,r.foodInfoDesc,r.foodInfoPrice,r.isSale FROM Food r WHERE r.foodInfoTitle LIKE %?1% AND r.deletedAt IS NULL")
    Page<Food> findByFoodInfoTitleContainingAndDeletedAtIsNull(String foodInfoTitle, Pageable pageable);

    @Query("SELECT r.foodInfoTitle,r.foodInfoDesc,r.foodInfoPrice,r.isSale,r.isHidden FROM Food r WHERE r.deletedAt IS NULL")
    Page<Food> getAllFoods(Pageable pageable);
}
