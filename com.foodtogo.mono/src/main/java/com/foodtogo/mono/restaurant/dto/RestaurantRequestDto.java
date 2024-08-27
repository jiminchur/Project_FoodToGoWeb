package com.foodtogo.mono.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDto {

    private UUID categoryId;
    private String area;
    private Boolean isOpened;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantIntroduce;
    private String restaurantImageUrl;
}
