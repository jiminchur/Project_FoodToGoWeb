package com.foodtogo.mono.order.controller;

import com.foodtogo.mono.Result;
import com.foodtogo.mono.order.dto.request.OrderPageDto;
import com.foodtogo.mono.order.dto.request.OrderRequestDto;
import com.foodtogo.mono.order.dto.request.UpdateOrderStatusDto;
import com.foodtogo.mono.order.dto.response.OrderResponseDto;
import com.foodtogo.mono.order.service.OrderService;
import com.foodtogo.mono.user.core.enums.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // 주문 등록
    @PostMapping("/restaurants/{restaurant_id}/orders")
    public ResponseEntity<Result<String>> createOrder(@RequestHeader("X-User-Id") UUID userId,
                                                      @PathVariable("restaurant_id") UUID restaurantId,
                                                      @RequestBody OrderRequestDto requestDto) {
        String message = orderService.createOrder(userId, restaurantId, requestDto);
        return new ResponseEntity<>(Result.of(message), HttpStatus.OK);
    }

    // 주문 단건 조회
    @GetMapping("/orders/{order_id}")
    public ResponseEntity<Result<OrderResponseDto>> getOrderInfo(@RequestHeader("X-User-Id") UUID userId,
                                                                 @PathVariable("order_id") UUID orderId) {
        OrderResponseDto orderResponseDto = orderService.getOrderInfo(userId, orderId);
        return new ResponseEntity<>(Result.of(orderResponseDto), HttpStatus.OK);
    }

    // 주문 내역 조회 for 가게
    @GetMapping("/restaurants/{restaurant_id}/orders")
    public ResponseEntity<Result<Page<OrderResponseDto>>> getOrderListForRestaurant(@RequestHeader("X-Role") String role,
                                                                                    @RequestHeader("X-User-Id") UUID userId,
                                                                                    @PathVariable("restaurant_id") UUID restaurantId,
                                                                                    OrderPageDto orderPageDto) {
        if (UserRoleEnum.valueOf(role).equals(UserRoleEnum.CUSTOMER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not OWNER.");
        }
        int size = orderPageDto.getValidatedSize();
        Page<OrderResponseDto> orderResponseDtoList = orderService.getOrderListForRestaurant(userId, restaurantId, orderPageDto.getPage(), size, orderPageDto.getSortBy());
        return new ResponseEntity<>(Result.of(orderResponseDtoList), HttpStatus.OK);
    }

    // 주문  조회 for 고객
    @GetMapping("/users/{user_id}/orders")
    public ResponseEntity<Result<Page<OrderResponseDto>>> getOrderListForUser(@RequestHeader("X-User-Id") UUID userId,
                                                                              @PathVariable("user_id") UUID targetUserId,
                                                                              OrderPageDto orderPageDto) {
        int size = orderPageDto.getValidatedSize();
        Page<OrderResponseDto> orderResponseDtoList = orderService.getOrderListForUser(userId, targetUserId, orderPageDto.getPage(), size, orderPageDto.getSortBy());
        return new ResponseEntity<>(Result.of(orderResponseDtoList), HttpStatus.OK);
    }

    // 주문 전체 조회 FOR 운영진
    @GetMapping("/orders")
    public ResponseEntity<Result<Page<OrderResponseDto>>> getOrderListAll(@RequestHeader("X-Role") String role,
                                                                          OrderPageDto orderPageDto) {

        UserRoleEnum.validateManagerOrMaster(role);
        int size = orderPageDto.getValidatedSize();
        Page<OrderResponseDto> orderResponseDtoList = orderService.getOrderListAll(orderPageDto.getPage(), size, orderPageDto.getSortBy());
        return new ResponseEntity<>(Result.of(orderResponseDtoList), HttpStatus.OK);
    }

    // 주문 내역 삭제
    @DeleteMapping("/orders/{order_id}")
    public ResponseEntity<Result<String>> deleteUserOrderInfo(@RequestHeader("X-User-Id") UUID userId,
                                                              @PathVariable("order_id") UUID orderId) {

        String message = orderService.deleteUserOrderInfo(userId, orderId);
        return new ResponseEntity<>(Result.of(message), HttpStatus.OK);
    }

    // 주문 취소 요청
    @PostMapping("/orders/{order_id}/cancel")
    public ResponseEntity<Result<String>> cancelOrder(@RequestHeader("X-User-Id") UUID userId,
                                                      @PathVariable("order_id") UUID orderId) {
        String message = orderService.cancelOrder(userId, orderId);
        return new ResponseEntity<>(Result.of(message), HttpStatus.OK);
    }

    // 주문 상태 업데이트
    @PatchMapping("/orders/{order_id}/status")
    public ResponseEntity<Result<OrderResponseDto>> updateOrderStatus(@RequestHeader("X-Role") String role,
                                                                      @RequestHeader("X-User-Id") UUID userId,
                                                                      @PathVariable("order_id") UUID orderId,
                                                                      @RequestBody UpdateOrderStatusDto updateOrderStatusDto) {
        if (UserRoleEnum.valueOf(role).equals(UserRoleEnum.CUSTOMER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is CUSTOMER");
        }
        OrderResponseDto orderResponseDto = orderService.updateOrderStatus(orderId, userId, updateOrderStatusDto);
        return new ResponseEntity<>(Result.of(orderResponseDto), HttpStatus.OK);
    }
}
