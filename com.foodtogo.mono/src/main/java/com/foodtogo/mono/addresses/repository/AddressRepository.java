package com.foodtogo.mono.addresses.repository;

import com.foodtogo.mono.addresses.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
