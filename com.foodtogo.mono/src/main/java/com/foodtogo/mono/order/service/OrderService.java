package com.foodtogo.mono.order.service;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.food.repository.FoodRepository;
import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.order.core.domain.OrderFood;
import com.foodtogo.mono.order.core.enums.OrderStatusEnum;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderFoodRepository orderFoodRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;


    // 주문 등록 (접수)
    @Transactional
    public String createOrder(UUID userId, UUID restaurantId, OrderRequestDto requestDto) {
        // 유저 확인
        User user = findUserId(userId);
        // 음식점 확인
        Restaurant restaurant = findRestaurantId(restaurantId);
        // 가게 오픈 여부
        if(!restaurant.getIsOpened()){
            throw new IllegalArgumentException("가게 오픈전입니다.");
        }
        // 주문 생성 (접수)
        Order order = new Order(user, restaurant, requestDto.getOrderType());
        // 음식 리스트를 OrderFood 객체로 변환 & OrderFood를 Order에 추가
        List<OrderFood> orderFoodList = requestDto.getFoodList().stream()
                .map(foodInfoDto -> {
                    Food foodInfo = foodRepository.findById(foodInfoDto.getFoodId())
                            .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 음식 정보입니다."));
                    return new OrderFood(order, foodInfo, foodInfoDto.getQuantity());
                })
                .collect(Collectors.toList());

        // 총 가격 계산
        BigDecimal totalOrderPrice = orderFoodList.stream()
                .map(OrderFood::getTotalSinglePrice) // OrderFood 객체의 totalSinglePrice를 가져옴
                .reduce(BigDecimal.ZERO, BigDecimal::add); // 모든 가격을 더함
        // 주문에 총 가격 설정
        order.setTotalOrderPrice(totalOrderPrice);
        // 주문한 음식 리스트 정보 설정
        order.setOrderFoodList(orderFoodList);
        // 변경사항 저장 - 처음 객체를 생성할 때는 저장해주는 게 좋다.
        orderRepository.save(order);

        return "[" + user.getUsername() + "]님 주문 접수 완료";
    }

    // 주문 단건 조회
    @Transactional
    public OrderResponseDto getOrderInfo(UUID userId, UUID orderId) {
        // 주문 확인
        Order order = findOrderId(orderId);
        if (!order.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문정보가 아닙니다.");
        }
        // 주문-음식 정보
        return new OrderResponseDto(order, findOrderFoodList(order));
    }

    // 주문 내역 조회 for 가게
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrderListForRestaurant(UUID userId, UUID restaurantId, int page, int size, String sortBy) {
        // 음식점 확인
        Restaurant restaurant = findRestaurantId(restaurantId);
        // 유저의 음식점 주인여부 확인
        if (!restaurant.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님 소유의 음식점이 아닙니다.");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        // 가게 주문 내역 리스트
        return orderRepository.findByRestaurantId(restaurantId, pageable)
                .map(order -> new OrderResponseDto(order, findOrderFoodList(order)));
    }

    // 주문 내역 조회 for 고객
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrderListForUser(UUID userId, UUID targetUserId, int page, int size, String sortBy) {
        // 유저 확인
        if (!userId.equals(targetUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "조회 시도한 정보가 회원님의 정보가 아닙니다.");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        // 고객의 주문 내역 리스트
        return orderRepository.findByUserId(userId, pageable)
                .map(order -> new OrderResponseDto(order, findOrderFoodList(order)));
    }

    // 주문 전체 조회 for 운영진
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrderListAll(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return orderRepository.findAll(pageable)
                .map(order -> new OrderResponseDto(order, findOrderFoodList(order)));
    }

    // 주문 내역 삭제
    @Transactional
    public String deleteUserOrderInfo(UUID userId, UUID orderId) {
        // 주문 확인
        Order order = findOrderId(orderId);
        // 회원 주문 여부 체크
        if (!order.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문내역이 아닙니다.");
        }
        // 주문 내역 삭제
        order.deleteUserOrderInfo(order.getUser().getUserId().toString());
        // 주문한 음식 내역 삭제
        orderFoodRepository.findByOrder(order).forEach(orderFood ->
                orderFood.deleteOrderFood(order.getUser().getUserId().toString())
        );
        return "[" + order.getUser().getUsername() + "]님 주문 내역 삭제 완료.";
    }

    // 주문 취소 요청
    @Transactional
    public String cancelOrder(UUID userId, UUID orderId) {
        // 주문 조회
        Order order = findOrderId(orderId);
        // 회원 주문 여부 체크
        if (!order.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문내역이 아닙니다.");
        }
        if (!canCancelOrder(order)) {
            throw new IllegalArgumentException("주문을 접수한지 5분이 지났기 때문에 취소가 불가합니다.");
        }
        order.cancelOrder();

        return "주문 취소 완료.";
    }

    // 주문 상태 업데이트
    @Transactional
    public OrderResponseDto updateOrderStatus(UUID orderId, UUID userId, UpdateOrderStatusDto updateOrderStatusDto) {
        // 주문 확인
        Order order = findOrderId(orderId);
        // 주문 받은 가게 주인 여부 확인
        if (!order.getRestaurant().getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님 소유의 음식점이 아닙니다.");
        }
        // 주문 상태 변경
        order.updateOrderStatus(OrderStatusEnum.valueOf(updateOrderStatusDto.getOrderStatus()));

        return new OrderResponseDto(order, findOrderFoodList(order));
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

    // 회원 정보 찾는 공통 메소드
    private User findUserId(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 회원입니다."));
    }

    // 주문 정보 찾는 공통 메소드
    private Order findOrderId(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 주문 정보입니다."));
    }

    // 음식점 정보 찾는 공통 메소드
    private Restaurant findRestaurantId(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 음식점입니다."));
    }

}
