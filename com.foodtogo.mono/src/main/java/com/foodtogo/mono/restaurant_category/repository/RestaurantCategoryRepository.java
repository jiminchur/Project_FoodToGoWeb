package com.foodtogo.mono.restaurant_category.repository;

import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, UUID> {
}
