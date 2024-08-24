package com.foodtogo.mono.users.repository;

import com.foodtogo.mono.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
