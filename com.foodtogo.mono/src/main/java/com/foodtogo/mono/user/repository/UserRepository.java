package com.foodtogo.mono.user.repository;

import com.foodtogo.mono.user.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);


    // Default 메소드를 사용하여 findById의 Optional을 내부적으로 처리
    default User findUserId(UUID userId) {
        return findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId.toString()));
    }

    // 회원 목록 조회 (정렬 및 페이징)
    Page<User> findAll(Pageable pageable);


    // 회원 목록 조회 메서드: 특정 키워드가 포함된 사용자를 검색합니다.
    Page<User> findByUsernameContaining(String keyword, Pageable pageable);

    default User findUserByIdOrThrowException(UUID userId) {
        return findById(userId)
                .filter(user -> user.getDeletedAt() == null)  // 삭제되지 않은 유저인지 확인
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or has been deleted"));
    }

}
