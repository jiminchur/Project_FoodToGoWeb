package com.foodtogo.mono.restaurant_category.core;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoriesRequestDto;
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
public class RestaurantCategory extends LogEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "category_id")
    private UUID categoryId;

    @Column(name = "category_title",length = 50, unique = true)
    private String categoryTitle;

    // 카테고리 생성
    public static RestaurantCategory createCategories(
            RestaurantCategoriesRequestDto restaurantCategoriesRequest,
            String createBy
    ){
        RestaurantCategory restaurantCategory = RestaurantCategory.builder()
                .categoryTitle(restaurantCategoriesRequest.getCategoryTitle())
                .build();

        restaurantCategory.setCreatedBy(createBy);

        return restaurantCategory;
    }

    // 카테고리 수정
    public void updateCategories(
            String categoryTitle
    ){
        this.categoryTitle = categoryTitle;
    }
}
