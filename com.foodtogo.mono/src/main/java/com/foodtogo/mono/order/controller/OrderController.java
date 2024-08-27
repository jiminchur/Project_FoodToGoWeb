package com.foodtogo.mono.order.controller;

import com.foodtogo.mono.order.dto.request.OrderRequestDto;
import com.foodtogo.mono.order.dto.response.OrderResponseDto;
import com.foodtogo.mono.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    // 음식점에 속한 주문 전체 조회 for 가게
    // 주문  조회 for 고객
    // 주문 전체 조회 FOR 운영진
    // 주문 내역 삭제
    // 주문 취소 요청
    // 주문 상태 업데이트
}
