package com.foodtogo.mono.address.repository;

import com.foodtogo.mono.address.core.domain.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {

    @Query("SELECT p FROM Address p WHERE p.user.id = :userId")
    Page<Address> findByUserId(UUID userId, Pageable pageable);
}
