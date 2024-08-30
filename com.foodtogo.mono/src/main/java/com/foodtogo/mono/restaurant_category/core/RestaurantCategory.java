package com.foodtogo.mono.restaurant_category.core;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoryRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_restaurant_categories")
public class RestaurantCategory extends LogEntity {

    @Id
    @UuidGenerator
    private UUID categoryId;

    @Column(length = 50, unique = true)
    private String categoryTitle;

    // 카테고리 생성
    public RestaurantCategory(
            RestaurantCategoryRequestDto restaurantCategoriesRequest,
            String createBy
    ){
        this.categoryTitle = restaurantCategoriesRequest.getCategoryTitle();

        setCreatedBy(createBy);
    }

    // 카테고리 수정
    public void updateCategories(
            RestaurantCategoryRequestDto restaurantCategoriesRequest,
            String updatedBy
    ){
        this.categoryTitle = restaurantCategoriesRequest.getCategoryTitle();

        setUpdatedBy(updatedBy);
    }

    // 카테고리 삭제
    public void deleteCategory(
            String deleteBy
    ) {
        this.deletedBy = deleteBy;
        this.deletedAt = LocalDateTime.now();
    }
}
