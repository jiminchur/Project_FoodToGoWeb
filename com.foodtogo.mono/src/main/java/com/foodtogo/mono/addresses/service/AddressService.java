package com.foodtogo.mono.addresses.service;

import com.foodtogo.mono.addresses.dto.request.AddressRequestDto;
import com.foodtogo.mono.addresses.entity.Address;
import com.foodtogo.mono.addresses.repository.AddressRepository;
import com.foodtogo.mono.users.entity.User;
import com.foodtogo.mono.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    // 회원 배송지 등록
    public String createAddress(UUID userId, AddressRequestDto requestDto) {

        // 유저 체크? 사실 필요없을 부분 같음.
        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Address address = new Address(user, requestDto);
        addressRepository.save(address);
        return "[" + user.getUsername() + "] 회원님 배송지 등록 완료";
    }
}
