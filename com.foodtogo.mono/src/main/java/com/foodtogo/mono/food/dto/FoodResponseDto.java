package com.foodtogo.mono.food.dto;

import com.foodtogo.mono.food.core.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodResponseDto {

    private UUID foodId;
    private String foodInfoTitle;
    private String foodInfoDesc;
    private BigDecimal foodInfoPrice;
    private Boolean isHidden;
    private Boolean isSale;

    public FoodResponseDto(
            Food food
    ) {
        this.foodId = food.getFoodInfoId();
        this.foodInfoTitle = food.getFoodInfoTitle();
        this.foodInfoDesc = food.getFoodInfoDesc();
        this.foodInfoPrice = food.getFoodInfoPrice();
        this.isHidden = food.getIsHidden();
        this.isSale = food.getIsSale();
    }
}
