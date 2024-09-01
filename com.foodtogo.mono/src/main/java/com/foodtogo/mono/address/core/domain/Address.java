package com.foodtogo.mono.address.core.domain;

import com.foodtogo.mono.address.dto.request.AddressRequestDto;
import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.user.core.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    @Column(name = "address_id", columnDefinition = "UUID", nullable = false, unique = true)
    private UUID addressId;

    // user는 User 객체를 참조하여 JPA가 객체 간의 관계를 관리
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 외래 키 매핑
    private User user; // User 객체를 참조하는 필드

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "request", columnDefinition = "TEXT")
    private String request;


    // 회원 배송지 등록
    public Address(User user, AddressRequestDto requestDto) {
        this.address = requestDto.getAddress();
        this.request = requestDto.getRequest();
        this.user = user;
        setCreatedBy(user.getUsername());
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
