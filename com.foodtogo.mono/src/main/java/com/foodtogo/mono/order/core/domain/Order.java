package com.foodtogo.mono.order.core.domain;

import com.foodtogo.mono.log.BaseEntity;
import com.foodtogo.mono.order.core.enums.OrderStatusEnum;
import com.foodtogo.mono.order.core.enums.OrderTypeEnum;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.user.core.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_orders")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Order extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID orderId;

    @Column(nullable = false)
    private OrderTypeEnum orderType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEnum orderStatus = OrderStatusEnum.PENDING;

    @Column
    @Enumerated(EnumType.STRING)
    private BigDecimal totalOrderPrice;

    @Column(nullable = false)
    private Boolean isPaid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderFood> orderFoodList;


    // 주문 등록 (접수)
    public Order(User user, Restaurant restaurant, String orderType) {
        this.user = user;
        this.restaurant = restaurant;
        this.orderType = OrderTypeEnum.valueOf(orderType);
        this.totalOrderPrice = BigDecimal.ZERO;
        this.isPaid = false;
    }

    // 주문 상태 변경
    public void updateOrderStatus(OrderStatusEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    // 주문 내역 삭제
    public void deleteUserOrderInfo(String deletedBy) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = deletedBy;
    }

    // 주문 취소
    public void cancelOrder(String username) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
        this.orderStatus = OrderStatusEnum.CANCELED;
    }

    // 주문한 음식 리스트 지정
    public void setOrderFoodList(List<OrderFood> orderFoodList) {this.orderFoodList = orderFoodList;}

    // 주문 음식 총 계산 비용 지정
    public void setTotalOrderPrice(BigDecimal totalOrderPrice) {this.totalOrderPrice = totalOrderPrice;}
}
