package com.foodtogo.mono.address.core.domain;

import com.foodtogo.mono.address.dto.request.AddressRequestDto;
import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.user.core.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends LogEntity {

    @Id
    @UuidGenerator
    private UUID addressId;

    @Column(nullable = false)
    private String address;

    @Column
    private String request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user-id", nullable = false)
    private User user;

    // 회원 배송지 등록
    public Address(User userId, AddressRequestDto requestDto) {
        this.address = requestDto.getAddress();
        this.request = requestDto.getRequest();
        this.user = userId;
        setCreatedBy(userId.getUsername());
    }

    // 회원 배송지 정보 수정
    public void updateAddressInfo(AddressRequestDto requestDto, String updatedBy) {
        this.address = requestDto.getAddress();
        this.request = requestDto.getRequest();
        setUpdatedBy(updatedBy);
    }

    // 회원 배송지 정보 삭제
    public void deleteAddress(String username) {
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(username);
    }
}
