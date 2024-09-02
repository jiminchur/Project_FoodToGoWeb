package com.foodtogo.mono.payment.dto.response;

import com.foodtogo.mono.payment.core.domain.Payment;
import com.foodtogo.mono.payment.core.enums.PaymentStatusEnum;
import com.foodtogo.mono.payment.core.enums.PaymentTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private UUID paymentId;
    private UUID orderId;
    private UUID restaurantId;
    private String restaurantName;
    private BigDecimal paymentPrice;
    private PaymentTypeEnum paymentType;
    private PaymentStatusEnum paymentStatus;

    public PaymentResponseDto(Payment payment) {
        this.paymentId = payment.getPaymentId();
        this.orderId = payment.getOrder().getOrderId();
        this.restaurantId = payment.getOrder().getRestaurant().getRestaurantId();
        this.restaurantName = payment.getOrder().getRestaurant().getRestaurantName();
        this.paymentPrice = payment.getAmount();
        this.paymentType = payment.getPaymentType();
        this.paymentStatus = payment.getPaymentStatus();
    }
}
