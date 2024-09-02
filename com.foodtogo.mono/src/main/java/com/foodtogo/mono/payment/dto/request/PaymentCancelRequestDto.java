package com.foodtogo.mono.payment.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCancelRequestDto {
    private String paymentStatus;
}
