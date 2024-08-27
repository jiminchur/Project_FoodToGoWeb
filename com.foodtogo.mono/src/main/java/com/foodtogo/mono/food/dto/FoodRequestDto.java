package com.foodtogo.mono.food.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequestDto {

    private String foodInfoTitle;
    private String foodInfoDesc;
    private BigDecimal foodInfoPrice;
}
