package com.foodtogo.mono.payment.core.domain;

import com.foodtogo.mono.log.BaseEntity;
import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.payment.core.enums.PaymentStatusEnum;
import com.foodtogo.mono.payment.core.enums.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "p_payments")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Payment extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID paymentId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum paymentType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatusEnum paymentStatus;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // 결제 생성(요청)
    public Payment(Order order, PaymentTypeEnum paymentType, BigDecimal amount) {
        this.order = order;
        this.paymentType = paymentType;
        this.paymentStatus = PaymentStatusEnum.PENDING;
        this.amount = amount;
    }

    // 결제 취소 요청
    public void cancelPayment(PaymentStatusEnum paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
