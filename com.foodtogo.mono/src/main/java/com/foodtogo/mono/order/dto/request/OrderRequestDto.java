package com.foodtogo.mono.order.dto.request;

import com.foodtogo.mono.order.dto.response.OrderFoodInfoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {
    private String orderType;
    private List<OrderFoodInfoDto> foodList;
}
