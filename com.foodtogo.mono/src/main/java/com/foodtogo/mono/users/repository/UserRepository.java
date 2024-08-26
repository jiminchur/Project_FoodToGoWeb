package com.foodtogo.mono.users.repository;

import com.foodtogo.mono.users.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Page<User> findByUsernameContaining(String keyword, Pageable pageable);
}
