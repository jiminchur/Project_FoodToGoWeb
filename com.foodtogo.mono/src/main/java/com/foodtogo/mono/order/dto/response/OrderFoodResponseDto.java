package com.foodtogo.mono.order.dto.response;

import com.foodtogo.mono.order.core.domain.OrderFood;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderFoodResponseDto {
    private UUID orderFoodId;
    private Integer count;
    private BigDecimal singlePrice;
    private BigDecimal totalSinglePrice;


    public OrderFoodResponseDto(OrderFood orderFood) {
        this.orderFoodId = orderFood.getOrderFoodId();
        this.count = orderFood.getCount();
        this.singlePrice = orderFood.getSinglePrice();
        this.totalSinglePrice = orderFood.getTotalSinglePrice();
    }
}
