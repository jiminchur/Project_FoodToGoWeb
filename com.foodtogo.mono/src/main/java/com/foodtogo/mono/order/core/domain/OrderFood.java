package com.foodtogo.mono.order.core.domain;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.log.LogEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "p_orders_foods")
@Getter
@Setter
public class OrderFood extends LogEntity {

    @Id
    @UuidGenerator
    private UUID orderFoodId;

    @Column(nullable = false)
    private Integer count;

    @Column // 이녀석 필요할까?
    private BigDecimal singlePrice;

    @Column
    private BigDecimal totalSinglePrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    // 주문-음식 등록
    public OrderFood(String username, Order orderId, Food foodInfoId, Integer count) {
        this.count = count;
        this.singlePrice = foodInfoId.getFoodInfoPrice();
        this.totalSinglePrice = foodInfoId.getFoodInfoPrice().multiply(BigDecimal.valueOf(count));
        this.order = orderId;
        this.food = foodInfoId;
        setCreatedBy(username);
    }
}
