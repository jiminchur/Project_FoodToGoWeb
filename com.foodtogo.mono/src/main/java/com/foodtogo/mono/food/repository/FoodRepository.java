package com.foodtogo.mono.food.repository;

import com.foodtogo.mono.food.core.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoodRepository extends JpaRepository<Food, UUID> {
}
