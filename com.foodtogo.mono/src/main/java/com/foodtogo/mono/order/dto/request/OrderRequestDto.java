package com.foodtogo.mono.order.dto.request;

import com.foodtogo.mono.order.dto.response.OrderFoodInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private Boolean isOnline;
    private List<OrderFoodInfoDto> foodList;
}
