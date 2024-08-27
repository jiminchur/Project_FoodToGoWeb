package com.foodtogo.mono.order.controller;

import com.foodtogo.mono.order.dto.request.OrderRequestDto;
import com.foodtogo.mono.order.dto.request.UpdateOrderStatusDto;
import com.foodtogo.mono.order.dto.response.OrderResponseDto;
import com.foodtogo.mono.order.service.OrderService;
import com.foodtogo.mono.user.core.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 등록
    @PostMapping("/restaurants/{restaurant_id}/orders")
    public ResponseEntity<String> createOrder(@RequestHeader("X-User-Id") UUID userId,
                                              @PathVariable("restaurant_id") UUID restaurantId,
                                              @RequestBody OrderRequestDto requestDto) {
        String message = orderService.createOrder(userId, restaurantId, requestDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 주문 단건 조회
    @GetMapping("/orders/{order_id}")
    public ResponseEntity<OrderResponseDto> getOrderInfo(@RequestHeader("X-User-Id") UUID userId,
                                                         @PathVariable("order_id") UUID orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderInfo(userId, orderId);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }

    // 주문 내역 조회 for 가게
    @GetMapping("/restaurants/{restaurant_id}/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrderListForRestaurant(@RequestHeader("X-Role") String role,
                                                                            @RequestHeader("X-User-Id") UUID userId,
                                                                            @PathVariable("restaurant_id") UUID restaurantId) {
        if (!UserRoleEnum.valueOf(role).equals(UserRoleEnum.OWNER)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrderListForRestaurant(userId, restaurantId);
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    // 주문  조회 for 고객
    @GetMapping("/users/{user_id}/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrderListForUser(@RequestHeader("X-Role") String role,
                                                                      @PathVariable("user_id") UUID userId) {
        if (!UserRoleEnum.valueOf(role).equals(UserRoleEnum.CUSTOMER)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrderListForUser(userId);
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    // 주문 전체 조회 FOR 운영진
    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponseDto>> getOrderListAll(@RequestHeader("X-Role") String role) {

        if (!UserRoleEnum.valueOf(role).equals(UserRoleEnum.MASTER) && !UserRoleEnum.valueOf(role).equals(UserRoleEnum.MANAGER)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<OrderResponseDto> orderResponseDtoList = orderService.getOrderListAll();
        return new ResponseEntity<>(orderResponseDtoList, HttpStatus.OK);
    }

    // 주문 내역 삭제
    // 주문 취소 요청
    // 주문 상태 업데이트
    @PatchMapping("/orders/{order_id}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(@RequestHeader("X-Role") String role,
                                                              @PathVariable("order_id") UUID orderId,
                                                              @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        if (UserRoleEnum.valueOf(role).equals(UserRoleEnum.CUSTOMER)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        OrderResponseDto orderResponseDto = orderService.updateOrderStatus(orderId, updateOrderStatusDto);
        return new ResponseEntity<>(orderResponseDto, HttpStatus.OK);
    }
}
