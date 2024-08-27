package com.foodtogo.mono.restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRequestDto {

    private String area;
    private Boolean isOpened;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantIntroduce;
    private String restaurantImageUrl;
}
