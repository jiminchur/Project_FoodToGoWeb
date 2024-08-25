package com.foodtogo.mono.addresses.entity;

import com.foodtogo.mono.addresses.dto.request.AddressRequestDto;
import com.foodtogo.mono.addresses.util.LogEntity;
import com.foodtogo.mono.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends LogEntity {

    @Id
    @UuidGenerator
    private UUID address_id;

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
    public void updateAddressInfo(AddressRequestDto requestDto) {
        this.address = requestDto.getAddress();
        this.request = requestDto.getRequest();
    }
}
