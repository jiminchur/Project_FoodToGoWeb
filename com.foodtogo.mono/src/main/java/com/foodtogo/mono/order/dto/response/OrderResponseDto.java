package com.foodtogo.mono.order.dto.response;

import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.order.core.enums.OrderStatusEnum;
import com.foodtogo.mono.order.core.enums.OrderTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private UUID orderId;
    private UUID userId;
    private UUID restaurantId;
    private OrderTypeEnum orderType;
    private OrderStatusEnum orderStatus;
    private BigDecimal totalOrderPrice;
    private Boolean isPaid;
    private List<OrderFoodResponseDto> orderFoodList;

    public OrderResponseDto(Order order, List<OrderFoodResponseDto> orderFoodList) {
        this.orderId = order.getOrderId();
        this.userId = order.getUser().getUserId();
        this.restaurantId = order.getRestaurant().getRestaurantId();
        this.orderType = order.getOrderType();
        this.orderStatus = order.getOrderStatus();
        this.totalOrderPrice = order.getTotalOrderPrice();
        this.isPaid = order.getIsPaid();
        this.orderFoodList = orderFoodList;
    }
}
