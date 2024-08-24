package com.foodtogo.mono.restaurants.core.domain;

import com.foodtogo.mono.restaurants.core.enums.RestaurantsArea;
import com.foodtogo.mono.restaurants.dto.RestaurantsRequestDto;
import com.foodtogo.mono.restaurants.dto.RestaurantsResponseDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_restaurants")
public class Restaurants {

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
    private RestaurantsArea area;

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

    public static Restaurants createRestaurants(
            RestaurantsRequestDto requestDto
    ){
        return Restaurants.builder()
                // 지역 : 우선 광화문 1개
                .area(RestaurantsArea.광화문)
                // 오픈 상태 디폴드값 True : 가게 오픈 상태 / False는 가게 닫힘
                .isOpened(Boolean.TRUE)
                .restaurantName(requestDto.getRestaurantName())
                .restaurantAddress(requestDto.getRestaurantAddress())
                .restaurantPhoneNumber(requestDto.getRestaurantPhoneNumber())
                .restaurantIntroduce(requestDto.getRestaurantIntroduce())
                .restaurantImageUrl(requestDto.getRestaurantImageUrl())
                .build();
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

    public RestaurantsResponseDto toResponseDto(){
        return new RestaurantsResponseDto(
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

}
