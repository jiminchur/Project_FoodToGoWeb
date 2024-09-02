package com.foodtogo.mono.order.core.domain;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.log.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "p_orders_foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class OrderFood extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID orderFoodId;

    @Column(nullable = false)
    private Integer count;

    @Column
    private BigDecimal singlePrice;

    // 예를 들어, 단가와 수량을 곱한 값으로 설정
    @Column
    private BigDecimal totalSinglePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    // 주문-음식 등록
    public OrderFood(Order orderId, Food foodInfoId, Integer count) {
        this.count = count;
        this.singlePrice = foodInfoId.getFoodInfoPrice();
        this.totalSinglePrice = foodInfoId.getFoodInfoPrice().multiply(BigDecimal.valueOf(count));
        this.order = orderId;
        this.food = foodInfoId;
    }
}
