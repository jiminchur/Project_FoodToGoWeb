package com.foodtogo.mono.restaurant.core.domain;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.restaurant.core.enums.RestaurantArea;
import com.foodtogo.mono.restaurant.dto.RestaurantRequestDto;
import com.foodtogo.mono.restaurant.dto.RestaurantResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_restaurants")
public class Restaurant extends LogEntity {

    // 음식점 ID, PK
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "restaurant_id")
    private UUID restaurantId;

    // 음식점 카테고리 ID, FK

    // 음식점 소유자 ID, FK

    // 가게 지역 (일시적으로 광화문만)
    @Enumerated(EnumType.STRING)
    @Column(name = "area")
    private RestaurantArea area;

    // 음식점 운영상태
    @Column(name = "is_opened")
    private Boolean isOpened;

    // 음식점 이름
    @Column(name = "restaurant_name",length = 255,unique = true)
    private String restaurantName;

    // 음식점 주소
    @Column(name = "restaurant_address",length = 255,unique = true)
    private String restaurantAddress;

    // 음식점 전화번호
    @Column(name = "restaurant_phone_number", length = 255)
    private String restaurantPhoneNumber;

    // 음식점 소개
    @Lob
    @Column(name = "restaurant_introduce")
    private String restaurantIntroduce;

    // 이미지 URL
    @Lob
    @Column(name = "restaurant_image_url")
    private String restaurantImageUrl;

    public static Restaurant createRestaurants(
            RestaurantRequestDto requestDto,
            String createdBy
    ){
        Restaurant restaurant = Restaurant.builder()
                // 지역 : 우선 광화문 1개
                .area(RestaurantArea.광화문)
                // 오픈 상태 디폴드값 True : 가게 오픈 상태 / False는 가게 닫힘
                .isOpened(Boolean.TRUE)
                .restaurantName(requestDto.getRestaurantName())
                .restaurantAddress(requestDto.getRestaurantAddress())
                .restaurantPhoneNumber(requestDto.getRestaurantPhoneNumber())
                .restaurantIntroduce(requestDto.getRestaurantIntroduce())
                .restaurantImageUrl(requestDto.getRestaurantImageUrl())
                .build();
        // 생성자 삽입
        restaurant.setCreatedBy(createdBy);

        return restaurant;
    }

    public void updateRestaurants(
            String restaurantName,
            String restaurantAddress,
            String restaurantPhoneNumber,
            String restaurantIntroduce,
            String restaurantImageUrl
    ){
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantPhoneNumber = restaurantPhoneNumber;
        this.restaurantIntroduce = restaurantIntroduce;
        this.restaurantImageUrl = restaurantImageUrl;
    }
    // 삭제일자 / 삭제자 작성
    public void deleteRestaurants(
            String deletedBy
    ) {
        this.deletedBy = deletedBy;
        this.deletedAt = LocalDateTime.now();
    }

    public RestaurantResponseDto toResponseDto(){
        return new RestaurantResponseDto(
                this.restaurantId
                ,this.area.name()
                ,this.isOpened
                ,this.restaurantName
                ,this.restaurantAddress
                ,this.restaurantPhoneNumber
                ,this.restaurantIntroduce
                ,this.restaurantImageUrl
        );
    }

    public void setIsOpened(Boolean isOpened) {
        this.isOpened = isOpened;
    }
}
