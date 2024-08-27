package com.foodtogo.mono.restaurant_category.dto;

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
}
