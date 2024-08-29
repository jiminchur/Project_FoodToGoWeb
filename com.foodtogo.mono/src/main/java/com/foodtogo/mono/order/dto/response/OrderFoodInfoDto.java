package com.foodtogo.mono.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFoodInfoDto {
    private UUID foodId;
    private Integer quantity;
}
