package com.foodtogo.mono.restaurant_categories.core;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.restaurant_categories.dto.RestaurantCategoriesRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_restaurant_categories")
public class RestaurantCategories extends LogEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "category_title",length = 50, unique = true)
    private String categoryTitle;

    // 카테고리 생성
    public static RestaurantCategories createCategories(
            RestaurantCategoriesRequestDto restaurantCategoriesRequest,
            String createBy
    ){
        RestaurantCategories restaurantCategories = RestaurantCategories.builder()
                .categoryTitle(restaurantCategoriesRequest.getCategoryTitle())
                .build();

        restaurantCategories.setCreatedBy(createBy);

        return restaurantCategories;
    }

    // 카테고리 수정
    public void updateCategories(
            String categoryTitle
    ){
        this.categoryTitle = categoryTitle;
    }
}
