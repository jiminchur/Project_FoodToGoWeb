package com.foodtogo.mono.order.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderTypeEnum {
    ONLINE("ORDER_TYPE_ONLINE"),
    OFFLINE("ORDER_TYPE_OFFLINE");

    private final String type;
}
