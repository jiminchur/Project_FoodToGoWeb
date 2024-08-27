package com.foodtogo.auth.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<UsersEntity, String> {
	Optional<UsersEntity> findByUserId(UUID userId);
	Optional<UsersEntity> findByEmail(String email);
	@Query(value = "SELECT role FROM p_users WHERE user_id = :userId", nativeQuery = true)
	Optional<String> findRoleByUserId(@Param("userId") UUID userId);

}
