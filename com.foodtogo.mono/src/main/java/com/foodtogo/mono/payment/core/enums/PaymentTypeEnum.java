package com.foodtogo.mono.payment.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentTypeEnum {
    CARD("PAYMENT_TYPE_CARD");

    private final String type;
}
