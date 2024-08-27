package com.foodtogo.mono.food.dto;

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
}
