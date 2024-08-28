package com.foodtogo.mono.order.core.domain;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.order.core.enums.OrderStatusEnum;
import com.foodtogo.mono.order.core.enums.OrderTypeEnum;
import com.foodtogo.mono.order.dto.request.OrderRequestDto;
import com.foodtogo.mono.order.dto.request.UpdateOrderStatusDto;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.user.core.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends LogEntity {

    @Id
    @UuidGenerator
    private UUID orderId;

    @Column(nullable = false)
    private OrderTypeEnum orderType;

    @Column(nullable = false)
    private OrderStatusEnum orderStatus = OrderStatusEnum.PENDING;

    @Column
    private BigDecimal amount;

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
    public Order(User user, Restaurant restaurant, OrderRequestDto requestDto) {
        this.user = user;
        this.restaurant = restaurant;
        this.orderType = requestDto.getIsOnline() ? OrderTypeEnum.ONLINE : OrderTypeEnum.OFFLINE;
        this.amount = BigDecimal.ZERO;
        this.isPaid = false;
        setCreatedBy(user.getUsername()); // 생성자에서 username 설정
    }

    // 주문 상태 변경
    public void updateOrderStatus(UpdateOrderStatusDto orderStatusDto) {
        this.orderStatus = OrderStatusEnum.valueOf(orderStatusDto.getOrderStatus());
    }

    // 주문 내역 삭제
    public void deleteUserOrderInfo(String username) {
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(username);
    }

    // 주문 취소
    public void cancelOrder(String username) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = username;
        this.orderStatus = OrderStatusEnum.CANCELED;
    }
}
