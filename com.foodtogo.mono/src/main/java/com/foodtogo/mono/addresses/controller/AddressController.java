package com.foodtogo.mono.addresses.controller;

import com.foodtogo.mono.addresses.dto.request.AddressRequestDto;
import com.foodtogo.mono.addresses.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/{user_id}/delivery-addresses")
    public ResponseEntity<String> createAddress(@RequestHeader("X-User-Id") UUID userId,
                                                AddressRequestDto requestDto) {
        String message = addressService.createAddress(userId, requestDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
