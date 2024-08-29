package com.foodtogo.mono.restaurant_category.repository;

import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface RestaurantCategoryRepository extends JpaRepository<RestaurantCategory, UUID> {

    // 삭제되지 않는 카테고리 전체조회
    @Query("SELECT r FROM RestaurantCategory r WHERE r.deletedAt IS NULL")
    List<RestaurantCategory> findAllAndDeletedAtIsNull();
}
