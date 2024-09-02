package com.foodtogo.mono.payment.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PaymentStatusEnum {
    PENDING("PAYMENT_STATUS_PENDING"),
    COMPLETED("PAYMENT_STATUS_COMPLETED"),
    CANCELED("PAYMENT_STATUS_CANCELED");

    private final String status;
}
