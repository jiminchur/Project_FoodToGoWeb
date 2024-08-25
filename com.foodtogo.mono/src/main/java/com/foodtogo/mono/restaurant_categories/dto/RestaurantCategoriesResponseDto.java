package com.foodtogo.mono.restaurant_categories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantCategoriesResponseDto {

    private UUID categoryId;
    private String categoryTitle;
}
