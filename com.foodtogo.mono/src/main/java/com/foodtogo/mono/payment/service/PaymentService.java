package com.foodtogo.mono.payment.service;

import com.foodtogo.mono.order.core.domain.Order;
import com.foodtogo.mono.order.repository.OrderRepository;
import com.foodtogo.mono.payment.core.domain.Payment;
import com.foodtogo.mono.payment.core.enums.PaymentStatusEnum;
import com.foodtogo.mono.payment.core.enums.PaymentTypeEnum;
import com.foodtogo.mono.payment.dto.request.PaymentCancelRequestDto;
import com.foodtogo.mono.payment.dto.request.PaymentRequestDto;
import com.foodtogo.mono.payment.dto.response.PaymentResponseDto;
import com.foodtogo.mono.payment.repository.PaymentRepository;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    // 결제 생성(요청)
    @Transactional
    public String createPayment(UUID userId, PaymentRequestDto paymentRequestDto) {
        // 주문 여부 체크
        Order order = orderRepository.findById(paymentRequestDto.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 주문 정보입니다."));
        if (!order.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 주문내역이 아닙니다.");
        }
        // 주문에 대한 결제 존재여부 확인 (1:1)
        if(paymentRepository.existsByOrder(order)){
            throw new IllegalArgumentException("이미 결제가 완료된 주문입니다.");
        }
        // 결제 생성
        Payment payment = new Payment(order, PaymentTypeEnum.valueOf(paymentRequestDto.getPaymentType()), order.getTotalOrderPrice());
        paymentRepository.save(payment);
        // PG에 요청
        return "결제 요청 성공.";
    }

    // 결제 취소(요청) - 고객
    @Transactional
    public String cancelPayment(UUID userId, UUID paymentId, PaymentCancelRequestDto paymentCancelRequestDto) {
        // 결제 조회
        Payment payment = findPaymentId(paymentId);
        // 결제자 여부 체크
        if (!payment.getOrder().getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 결제내역이 아닙니다.");
        }
        // 결제 취소 요청
        payment.cancelPayment(PaymentStatusEnum.valueOf(paymentCancelRequestDto.getPaymentStatus()));
        // 결제 요청 5분 이내로 한정해야될까?
        // 가게 id 체크?
        // PG에 요청
        return "결제 취소 완료.";
    }

    // 결제 단건 조회
    @Transactional(readOnly = true)
    public PaymentResponseDto getPaymentInfo(UUID userId, UUID paymentId) {
        // 결제 조회
        Payment payment = findPaymentId(paymentId);
        // 결제자 여부 체크
        if (!payment.getOrder().getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 결제내역이 아닙니다.");
        }

        return new PaymentResponseDto(payment);
    }

    // 결제 목록 조회 - 고객
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getPaymentListForUser(UUID userId, UUID targetUserId, int page, int size, String sortBy) {
        // 회원 체크
        if (!userId.equals(targetUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "회원님의 정보가 아닙니다.");
        }
        // 페이징 처리
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Payment> paymentList = paymentRepository.findPaymentsByUserId(targetUserId, pageable);

        return paymentList.map(PaymentResponseDto::new);
    }

    // 결제 목록 조회 - 가게
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getPaymentListForRestaurant(UUID userId, UUID restaurantId, int page, int size, String sortBy) {
        // 음식점 존재 여부 체크
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식점 입니다."));
        // 음식점 주인 체크
        if (!restaurant.getUser().getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "회원님 소유의 음식점이 아닙니다.");
        }
        // 페이징 처리
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Payment> paymentList = paymentRepository.findPaymentsByRestaurantId(restaurantId, pageable);

        return paymentList.map(PaymentResponseDto::new);
    }

    // 결제 전체 조회 for 운영진
    @Transactional(readOnly = true)
    public Page<PaymentResponseDto> getPaymentListAll(int page, int size, String sortBy) {
        // 페이징 처리
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return paymentRepository.findAll(pageable)
                .map(PaymentResponseDto::new);
    }

    // 결제 정보 찾는 공통 메소드
    private Payment findPaymentId(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 결제 정보입니다."));
    }
}
