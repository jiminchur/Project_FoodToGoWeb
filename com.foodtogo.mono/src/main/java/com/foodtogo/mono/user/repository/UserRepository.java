package com.foodtogo.mono.user.repository;

import com.foodtogo.mono.user.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByUsernameContaining(String keyword, Pageable pageable);
}