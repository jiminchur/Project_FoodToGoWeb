package com.foodtogo.mono.addresses.controller;

import com.foodtogo.mono.addresses.dto.request.AddressRequestDto;
import com.foodtogo.mono.addresses.dto.response.AddressResponseDto;
import com.foodtogo.mono.addresses.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{user_id}/delivery-addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // 회원 배송지 등록
    @PostMapping
    public ResponseEntity<String> createAddress(@PathVariable("user_id") UUID userId,
                                                AddressRequestDto requestDto) {
        String message = addressService.createAddress(userId, requestDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 회원 배송지 목록 조회
    @GetMapping
    public ResponseEntity<Page<AddressResponseDto>> getUserAddressList(@RequestParam("page") int page,
                                                                       @RequestParam("size") int size,
                                                                       @RequestParam("sortBy") String sortBy,
                                                                       @RequestParam("isAsc") boolean isAsc,
                                                                       @PathVariable("user_id") UUID userId) {

        Page<AddressResponseDto> addressList = addressService.getUserAddressList(page, size, sortBy, isAsc, userId);
        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }

    // 회원 배송지 상세 조회
    // 회원 배송지 정보 수정
    // 회원 배송지 삭제
}
