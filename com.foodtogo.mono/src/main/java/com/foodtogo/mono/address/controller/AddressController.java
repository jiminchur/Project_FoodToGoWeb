package com.foodtogo.mono.address.controller;

import com.foodtogo.mono.Result;
import com.foodtogo.mono.address.dto.request.AddressRequestDto;
import com.foodtogo.mono.address.dto.response.AddressResponseDto;
import com.foodtogo.mono.address.service.AddressService;
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
    public ResponseEntity<Result<String>> createAddress(@PathVariable("user_id") UUID userId,
                                                        @RequestBody AddressRequestDto requestDto) {
        String message = addressService.createAddress(userId, requestDto);
        return new ResponseEntity<>(Result.of(message), HttpStatus.OK);
    }

    // 회원 배송지 목록 조회
    @GetMapping
    public ResponseEntity<Result<Page<AddressResponseDto>>> getUserAddressList(@RequestParam("page") int page,
                                                                               @RequestParam("size") int size,
                                                                               @RequestParam("sortBy") String sortBy,
                                                                               @PathVariable("user_id") UUID userId) {

        Page<AddressResponseDto> addressList = addressService.getUserAddressList(page, size, sortBy, userId);
        return new ResponseEntity<>(Result.of(addressList), HttpStatus.OK);
    }

    // 회원 배송지 상세 조회
    @GetMapping("{address_id}")
    public ResponseEntity<Result<AddressResponseDto>> getUserAddress(@PathVariable("user_id") UUID userId,
                                                                     @PathVariable("address_id") UUID addressId) {
        AddressResponseDto addressInfo = addressService.getUserAddress(userId, addressId);
        return new ResponseEntity<>(Result.of(addressInfo), HttpStatus.OK);
    }

    // 회원 배송지 정보 수정
    @PutMapping("{address_id}")
    public ResponseEntity<Result<AddressResponseDto>> updateAddressInfo(@PathVariable("user_id") UUID userId,
                                                                        @PathVariable("address_id") UUID addressId,
                                                                        @RequestBody AddressRequestDto requestDto) {
        AddressResponseDto updateAddressInfo = addressService.updateAddressInfo(userId, addressId, requestDto);
        return new ResponseEntity<>(Result.of(updateAddressInfo), HttpStatus.OK);
    }

    // 회원 배송지 정보 삭제
    @DeleteMapping("/{address_id}")
    public ResponseEntity<Result<String>> deleteAddress(@PathVariable("user_id") UUID userId,
                                                        @PathVariable("address_id") UUID addressId) {
        String message = addressService.deleteAddress(userId, addressId);
        return new ResponseEntity<>(Result.of(message), HttpStatus.OK);
    }
}
