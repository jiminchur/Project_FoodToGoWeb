package com.foodtogo.mono.address.core.domain;

import com.foodtogo.mono.log.BaseEntity;
import com.foodtogo.mono.user.core.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_addresses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Address extends BaseEntity {

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
    public Address(User userId, String address, String request) {
        this.address = address;
        this.request = request;
        this.user = userId;
    }

    // 회원 배송지 정보 수정
    public void updateAddressInfo(String address, String request) {
        this.address = address;
        this.request = request;
    }

    // 회원 배송지 정보 삭제
    public void deleteAddress(String deletedBy) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = deletedBy;
    }
}
