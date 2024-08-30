package com.foodtogo.mono.payment.core.domain;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.payment.core.enums.PaymentStatusEnum;
import com.foodtogo.mono.payment.core.enums.PaymentTypeEnum;
import com.foodtogo.mono.payment.dto.request.PaymentRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "p_payments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends LogEntity {

    @Id
    @UuidGenerator
    private UUID paymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentTypeEnum paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatusEnum paymentStatus;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 결제 생성(요청)
    public Payment(PaymentRequestDto paymentRequestDto, Order order) {
        this.order = order;
        this.paymentType = PaymentTypeEnum.valueOf(paymentRequestDto.getPaymentType());
        this.paymentStatus = PaymentStatusEnum.PENDING;
        this.amount = paymentRequestDto.getAmount();
        this.createdBy = order.getUser().getUsername();
    }

    // 결제 취소 요청
    public void cancelPayment(String paymentStatus) {
        this.paymentStatus = PaymentStatusEnum.valueOf(paymentStatus);
    }
}
