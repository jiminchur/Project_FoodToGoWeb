package com.foodtogo.mono.address.service;

import com.foodtogo.mono.address.core.domain.Address;
import com.foodtogo.mono.address.dto.request.AddressRequestDto;
import com.foodtogo.mono.address.dto.response.AddressResponseDto;
import com.foodtogo.mono.address.repository.AddressRepository;
import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    // 회원 배송지 등록
    @Transactional
    public String createAddress(UUID userId, AddressRequestDto requestDto) {

        User user = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Address address = new Address(user, requestDto.getAddress(), requestDto.getRequest());
        addressRepository.save(address);
        return "[" + user.getUsername() + "] 회원님 배송지 등록 완료";
    }

    // 회원 배송지 목록 조회
    @Transactional(readOnly = true)
    public Page<AddressResponseDto> getUserAddressList(int page, int size, String sortBy, UUID userId) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Address> addressList = addressRepository.findByUserId(userId, pageable);

        return addressList.map(AddressResponseDto::new);
    }

    // 회원 배송지 상세 조회
    @Transactional(readOnly = true)
    public AddressResponseDto getUserAddress(UUID userId, UUID addressId) {

        Address address = findAddressId(addressId);
        if (!address.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 배송지가 아닙니다.");
        }

        return new AddressResponseDto(address);
    }

    // 회원 배송지 정보 수정
    @Transactional
    public AddressResponseDto updateAddressInfo(UUID userId, UUID addressId, AddressRequestDto requestDto) {

        Address address = findAddressId(addressId);
        if (!address.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 배송지가 아닙니다.");
        }
        address.updateAddressInfo(requestDto.getAddress(), requestDto.getRequest());

        return new AddressResponseDto(address);
    }

    // 회원 배송지 정보 삭제
    @Transactional
    public String deleteAddress(UUID userId, UUID addressId) {

        Address address = findAddressId(addressId);
        if (!address.getUser().getUserId().equals(userId)) {
            throw new IllegalArgumentException("회원님의 배송지가 아닙니다.");
        }
        address.deleteAddress(address.getUser().getUsername());

        return "배송지 삭제 완료.";
    }


    // 배송지 공통 메소드
    public Address findAddressId(UUID addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 배송지입니다."));
    }
}
