package com.foodtogo.mono.users.service;

import com.foodtogo.mono.users.dto.response.UserResponseDto;
import com.foodtogo.mono.users.entity.User;
import com.foodtogo.mono.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 목록 조회 - MASTER
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUserList(int page, int size, String sortBy, boolean isAsc) {

        Pageable pageable = convertToPage(page, size, sortBy, isAsc);
        Page<User> userList = userRepository.findAll(pageable);

        return userList.map(UserResponseDto::new);
    }

    // 페이지 처리
    private Pageable convertToPage(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }
}