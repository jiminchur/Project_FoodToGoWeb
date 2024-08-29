package com.foodtogo.mono.restaurant_category.dto;

import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCategoryResponseDto {

    private UUID categoryId;
    private String categoryTitle;

    public RestaurantCategoryResponseDto(
            RestaurantCategory restaurantCategory
    ){
        this.categoryId = restaurantCategory.getCategoryId();
        this.categoryTitle = restaurantCategory.getCategoryTitle();
    }
}
