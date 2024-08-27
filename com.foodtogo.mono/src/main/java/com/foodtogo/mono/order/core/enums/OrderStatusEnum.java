package com.foodtogo.mono.order.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatusEnum {
    PENDING("ORDER_STATUS_PENDING"),
    DELIVERY("ORDER_STATUS_DELIVERY"),
    COMPLETED("ORDER_STATUS_COMPLETED"),
    CANCELED("ORDER_STATUS_CANCELED");

    private final String status;
}
