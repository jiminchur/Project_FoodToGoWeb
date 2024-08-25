package com.foodtogo.mono.addresses.service;

import com.foodtogo.mono.addresses.dto.request.AddressRequestDto;
import com.foodtogo.mono.addresses.dto.response.AddressResponseDto;
import com.foodtogo.mono.addresses.entity.Address;
import com.foodtogo.mono.addresses.repository.AddressRepository;
import com.foodtogo.mono.users.entity.User;
import com.foodtogo.mono.users.repository.UserRepository;
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
    public String createAddress(UUID userId, AddressRequestDto requestDto) {

        // 유저 체크? 사실 필요없을 부분 같음.
        User user = findUserId(userId);

        Address address = new Address(user, requestDto);
        addressRepository.save(address);
        return "[" + user.getUsername() + "] 회원님 배송지 등록 완료";
    }

    // 회원 배송지 목록 조회
    @Transactional(readOnly = true)
    public Page<AddressResponseDto> getUserAddressList(int page, int size, String sortBy, boolean isAsc, UUID userId) {

        User user = findUserId(userId);

        Pageable pageable = convertToPage(page, size, sortBy, isAsc);
        Page<Address> addressList = addressRepository.findByUser(user, pageable);

        return addressList.map(AddressResponseDto::new);
    }

    // 회원 배송지 상세 조회
    @Transactional(readOnly = true)
    public AddressResponseDto getUserAddress(UUID userId, UUID addressId) {

        User user = findUserId(userId);
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 주소입니다."));

        if(!user.getUser_id().equals(address.getUser().getUser_id())){
            throw new IllegalArgumentException("회원님의 배송지가 아닙니다.");
        }

        return new AddressResponseDto(address);
    }

    // 회원 배송지 정보 수정
    @Transactional
    public AddressResponseDto updateAddressInfo(UUID userId, UUID addressId, AddressRequestDto requestDto) {

        User user = findUserId(userId);
        Address address = addressRepository.findById(addressId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 주소입니다."));

        if(!user.getUser_id().equals(address.getUser().getUser_id())){
            throw new IllegalArgumentException("회원님의 배송지가 아닙니다.");
        }
        address.updateAddressInfo(requestDto);

        return new AddressResponseDto(address);
    }

    // 페이지 처리
    private Pageable convertToPage(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }

    // 유저 찾는 공통 메소드
    public User findUserId(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }
}
