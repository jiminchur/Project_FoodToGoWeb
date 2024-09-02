package com.foodtogo.mono.food.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FoodRequestDto {
    @NotNull(message = "foodInfoTitle must not be null")
    private String foodInfoTitle;

    @NotNull(message = "foodInfoDesc must not be null")
    private String foodInfoDesc;

    @NotNull(message = "foodInfoPrice must not be null")
    @Positive(message = "foodInfoPrice must be positive")
    private BigDecimal foodInfoPrice;
}
