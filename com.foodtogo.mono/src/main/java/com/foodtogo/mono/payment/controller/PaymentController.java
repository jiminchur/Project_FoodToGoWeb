package com.foodtogo.mono.payment.controller;

import com.foodtogo.mono.Result;
import com.foodtogo.mono.payment.dto.request.PaymentCancelRequestDto;
import com.foodtogo.mono.payment.dto.request.PaymentPageDto;
import com.foodtogo.mono.payment.dto.request.PaymentRequestDto;
import com.foodtogo.mono.payment.dto.response.PaymentResponseDto;
import com.foodtogo.mono.payment.service.PaymentService;
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
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 요청 (생성)
    @PostMapping("/payments")
    public ResponseEntity<Result<String>> createPayment(@RequestHeader("X-User-Id") UUID userId,
                                                        @RequestBody PaymentRequestDto paymentRequestDto) {
        String message = paymentService.createPayment(userId, paymentRequestDto);
        // 몇개, 얼마를 어떤 방식으로 결제할 것 인지?
        return new ResponseEntity<>(Result.of(message), HttpStatus.OK);
    }

    // 결제 취소 요청 Post /api/v1/payments/{payment_id}/refund
    @PostMapping("/payments/{payment_id}/refund")
    public ResponseEntity<Result<String>> cancelPayment(@RequestHeader("X-User-Id") UUID userId,
                                                        @PathVariable("payment_id") UUID paymentId,
                                                        @RequestBody PaymentCancelRequestDto paymentCancelRequestDto) {

        String message = paymentService.cancelPayment(userId, paymentId, paymentCancelRequestDto);
        return new ResponseEntity<>(Result.of(message), HttpStatus.OK);
    }

    // 결제 단건 조회 Get /api/v1/payments/{payment_id}
    @GetMapping("/payments/{payment_id}")
    public ResponseEntity<Result<PaymentResponseDto>> getPaymentInfo(@RequestHeader("X-User-Id") UUID userId,
                                                                     @PathVariable("payment_id") UUID paymentId) {
        PaymentResponseDto paymentResponseDto = paymentService.getPaymentInfo(userId, paymentId);
        return new ResponseEntity<>(Result.of(paymentResponseDto), HttpStatus.OK);
    }

    // 결제 목록 조회 - 유저
    @GetMapping("/users/{user_id}/payments")
    public ResponseEntity<Result<Page<PaymentResponseDto>>> getPaymentListForUser( @RequestHeader("X-User-Id") UUID userId,
                                                                                  @PathVariable("user_id") UUID targetUserId,
                                                                                  PaymentPageDto paymentPageDto) {
        int size = paymentPageDto.getValidatedSize();
        Page<PaymentResponseDto> paymentResponseDtoList = paymentService.getPaymentListForUser(userId, targetUserId, paymentPageDto.getPage(), size, paymentPageDto.getSortBy());
        return new ResponseEntity<>(Result.of(paymentResponseDtoList), HttpStatus.OK);
    }

    // 결제 목록 조회 - 가게
    @GetMapping("/restaurants/{restaurant_id}/payments")
    public ResponseEntity<Result<Page<PaymentResponseDto>>> getPaymentListForRestaurant(@RequestHeader("X-Role") String role,
                                                                                        @RequestHeader("X-User-Id") UUID userId,
                                                                                        @PathVariable("restaurant_id") UUID restaurantId,
                                                                                        PaymentPageDto paymentPageDto) {
        if (UserRoleEnum.valueOf(role).equals(UserRoleEnum.CUSTOMER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are Role is CUSTOMER");
        }
        int size = paymentPageDto.getValidatedSize();
        Page<PaymentResponseDto> paymentResponseDtoList = paymentService.getPaymentListForRestaurant(userId, restaurantId, paymentPageDto.getPage(), size, paymentPageDto.getSortBy());
        return new ResponseEntity<>(Result.of(paymentResponseDtoList), HttpStatus.OK);
    }

    // 결제 전체 조회 - 관리자
    @GetMapping("/payments")
    public ResponseEntity<Result<Page<PaymentResponseDto>>> getPaymentListAll(@RequestHeader("X-Role") String role,
                                                                              @RequestParam("page") int page,
                                                                              @RequestParam("size") int size,
                                                                              @RequestParam("sortBy") String sortBy) {
        UserRoleEnum.validateManagerOrMaster(role);
        Page<PaymentResponseDto> paymentResponseDtoList = paymentService.getPaymentListAll(page, size, sortBy);
        return new ResponseEntity<>(Result.of(paymentResponseDtoList), HttpStatus.OK);
    }
}
