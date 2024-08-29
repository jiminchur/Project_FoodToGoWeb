package com.foodtogo.mono.restaurant.dto;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.restaurant.core.enums.RestaurantArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDto {
    private UUID restaurantId;
    private RestaurantArea area;
    private Boolean isOpened;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantIntroduce;
    private String restaurantImageUrl;

    public RestaurantResponseDto(
            Restaurant restaurant
    ) {
        this.restaurantId = restaurant.getRestaurantId();
        this.area = restaurant.getArea();
        this.isOpened = restaurant.getIsOpened();
        this.restaurantName = restaurant.getRestaurantName();
        this.restaurantAddress = restaurant.getRestaurantAddress();
        this.restaurantPhoneNumber = restaurant.getRestaurantPhoneNumber();
        this.restaurantIntroduce = restaurant.getRestaurantIntroduce();
        this.restaurantImageUrl = restaurant.getRestaurantImageUrl();
    }
}
