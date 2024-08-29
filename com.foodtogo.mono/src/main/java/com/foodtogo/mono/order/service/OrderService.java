package com.foodtogo.mono.order.service;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.food.repository.FoodRepository;
import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.order.core.domain.OrderFood;
import com.foodtogo.mono.order.dto.request.OrderRequestDto;
import com.foodtogo.mono.order.dto.request.UpdateOrderStatusDto;
import com.foodtogo.mono.order.dto.response.OrderFoodResponseDto;
import com.foodtogo.mono.order.dto.response.OrderResponseDto;
import com.foodtogo.mono.order.repository.OrderFoodRepository;
import com.foodtogo.mono.order.repository.OrderRepository;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.restaurant.repository.RestaurantRepository;
import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderFoodRepository orderFoodRepository;

  
    // 주문 등록 (접수)
    @Transactional
    public String createOrder(UUID userId, UUID restaurantId, OrderRequestDto requestDto) {
        // 유저 확인
        User user = findUserId(userId);
        // 음식점 확인
        Restaurant restaurant = findRestaurant(restaurantId);
        // 주문 생성 (접수)
        Order order = new Order(user, restaurant, requestDto);
        // 음식 리스트를 OrderFood 객체로 변환하고, OrderFood를 Order에 추가
        List<OrderFood> orderFoodList = requestDto.getFoodList().stream()
                .map(foodInfoDto -> {
                    Food foodInfo = foodRepository.findById(foodInfoDto.getFoodId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식 정보입니다."));
                    return new OrderFood(user.getUsername(), order, foodInfo, foodInfoDto.getQuantity());
                })
                .collect(Collectors.toList());

        order.setOrderFoodList(orderFoodList);
        // 주문 저장 - 연관된 OrderFood도 함께 저장
        orderRepository.save(order);

        return "[" + user.getUsername() + "]님 주문 접수 완료";
    }

    // 주문 단건 조회
    @Transactional
    public OrderResponseDto getOrderInfo(UUID userId, UUID orderId) {
        // 주문 확인
        Order order = findOrderId(orderId);
        // 유저 확인
        if (!order.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문정보가 아닙니다.");
        }
        // 주문-음식 정보
        return new OrderResponseDto(order, findOrderFoodList(order));
    }

    // 주문 내역 조회 for 가게
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrderListForRestaurant(UUID userId, UUID restaurantId) {
        // 음식점 확인
        Restaurant restaurant = findRestaurant(restaurantId);
        // 유저 확인
        if (!restaurant.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님 소유의 음식점이 아닙니다.");
        }

        return orderRepository.findByRestaurant(restaurant).stream()
                .map(order -> new OrderResponseDto(order, findOrderFoodList(order)))
                .toList();
    }

    // 주문 내역 조회 for 고객
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrderListForUser(UUID userId) {
        // 유저 확인
        User user = findUserId(userId);

        return orderRepository.findByUser(user).stream()
                .map(order -> new OrderResponseDto(order, findOrderFoodList(order)))
                .toList();
    }

    // 주문 전체 조회 FOR 운영진
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getOrderListAll() {

        return orderRepository.findAll().stream()
                .map(order -> new OrderResponseDto(order, findOrderFoodList(order)))
                .toList();
    }

    // 주문 내역 삭제
    @Transactional
    public String deleteUserOrderInfo(UUID userId, UUID orderId) {
        // 유저 확인
        User user = findUserId(userId);
        // 주문 확인
        Order order = findOrderId(orderId);
        // 회원 주문 여부 체크
        if (!order.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문내역이 아닙니다.");
        }
        order.deleteUserOrderInfo(user.getUsername());
        return "[" + user.getUsername() + "]님 주문 내역 삭제 완료.";
    }

    // 주문 취소 요청
    public String cancelOrder(UUID userId, UUID orderId) {
        // 유저 조회
        User user = findUserId(userId);
        // 주문 조회
        Order order = findOrderId(orderId);
        // 회원 주문 여부 체크
        if (!order.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문내역이 아닙니다.");
        }
        if (!canCancelOrder(order)) {
            return "주문을 접수한지 5분이 지났기 때문에 취소가 불가합니다.";
        }
        order.cancelOrder(user.getUsername());
        return "주문 취소 완료.";
    }

    // 주문 상태 업데이트
    @Transactional
    public OrderResponseDto updateOrderStatus(UUID orderId, UpdateOrderStatusDto updateOrderStatusDto) {
        // 주문 확인
        Order order = findOrderId(orderId);
        // 주문 상태 변경
        order.updateOrderStatus(updateOrderStatusDto);

        return new OrderResponseDto(order, findOrderFoodList(order));
    }

    // 주문 정보 찾는 공통 메소드
    private Order findOrderId(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 주문 정보입니다."));
    }

    // 회원 정보 찾는 공통 메소드
    private User findUserId(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    // 음식점 정보 찾는 공통 메소드
    private Restaurant findRestaurant(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식점입니다."));
    }

    // 주문한 음식 정보 찾는 공통 메소드(OrderFood -> OrderFoodResponseDto 변환)
    private List<OrderFoodResponseDto> findOrderFoodList(Order order) {
        List<OrderFood> orderFoodList = orderFoodRepository.findByOrder(order);
        return orderFoodList.stream()
                .map(OrderFoodResponseDto::new).toList();
    }

    // 주문 취소 가능 여부 확인
    private boolean canCancelOrder(Order order) {
        LocalDateTime cancelDeadline = order.getCreatedAt().plusMinutes(5);

        return LocalDateTime.now().isBefore(cancelDeadline);
    }
}
