package com.foodtogo.mono.restaurant_categories.repository;

import com.foodtogo.mono.restaurant_categories.core.RestaurantCategories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantCategoriesRepository extends JpaRepository<RestaurantCategories, UUID> {
}
