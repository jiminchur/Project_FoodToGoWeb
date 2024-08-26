package com.foodtogo.mono.addresses.repository;

import com.foodtogo.mono.addresses.core.domain.Address;
import com.foodtogo.mono.user.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Page<Address> findByUser(User user, Pageable pageable);
}
