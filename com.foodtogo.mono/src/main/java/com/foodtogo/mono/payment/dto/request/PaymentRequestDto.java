package com.foodtogo.mono.payment.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentRequestDto {
    private UUID orderId;
    private String paymentType;
}
