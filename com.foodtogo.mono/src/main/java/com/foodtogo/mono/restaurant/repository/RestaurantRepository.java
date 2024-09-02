package com.foodtogo.mono.restaurant.repository;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    // 가게 검색
    @Query("SELECT r.restaurantName, r.restaurantIntroduce, r.restaurantAddress, r.isOpened, r.area, r.restaurantImageUrl, r.category " +
            "FROM Restaurant r WHERE r.restaurantName LIKE %?1% AND r.deletedAt IS NULL")
    Page<Restaurant> findByRestaurantNameContaining(String restaurantName, Pageable pageable);

    // 가게 전체 조회
    @Query("SELECT r.restaurantName, r.restaurantIntroduce, r.restaurantAddress, r.isOpened, r.area, r.restaurantImageUrl, r.category " +
            "FROM Restaurant r WHERE r.deletedAt IS NULL")
    Page<Restaurant> findAllAndDeletedAtIsNull(Pageable pageable);

}
