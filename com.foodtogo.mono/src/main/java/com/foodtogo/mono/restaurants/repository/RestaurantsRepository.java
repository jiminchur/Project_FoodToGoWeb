package com.foodtogo.mono.restaurants.repository;

import com.foodtogo.mono.restaurants.core.domain.Restaurants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantsRepository extends JpaRepository<Restaurants, UUID> {

    // 전체 조회
    Page<Restaurants> findAll(Pageable pageable);
}
