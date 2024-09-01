package com.foodtogo.auth.repository;

import com.foodtogo.auth.user.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, String> {
	boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	@Query(value = "SELECT role FROM p_users WHERE user_id = :userId", nativeQuery = true)
	Optional<String> findRoleByUserId(@Param("userId") UUID userId);

}
