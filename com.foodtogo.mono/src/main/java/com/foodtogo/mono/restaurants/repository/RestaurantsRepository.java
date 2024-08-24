package com.foodtogo.mono.restaurants.repository;

import com.foodtogo.mono.restaurants.core.Restaurants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantsRepository extends JpaRepository<Restaurants, UUID> {
}
