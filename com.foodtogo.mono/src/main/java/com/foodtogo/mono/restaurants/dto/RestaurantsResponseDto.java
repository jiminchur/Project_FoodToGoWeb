package com.foodtogo.mono.restaurants.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantsResponseDto {
    private UUID restaurantId;
    private String area;
    private Boolean isOpened;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantIntroduce;
    private String restaurantImageUrl;
}
