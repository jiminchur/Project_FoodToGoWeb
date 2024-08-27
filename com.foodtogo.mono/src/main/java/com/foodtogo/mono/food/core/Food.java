package com.foodtogo.mono.food.core;


import com.foodtogo.mono.food.dto.FoodRequestDto;
import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "p_food_infos")
public class Food extends LogEntity {

    // 음식 아이템 ID, PK
    @Id
    @UuidGenerator
    private UUID foodInfoId;

    // 음식점 ID, Fk, restaurant_id
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "restaurant_id", nullable = false )
    private Restaurant restaurant;

    // 음식 아이템 이름
    private String foodInfoTitle;

    // 음식 아이템 설명
    @Column(columnDefinition = "TEXT")
    private String foodInfoDesc;

    // 음식 아이템 가격
    private BigDecimal foodInfoPrice;

    // 음식 아이템 숨김 여부
    private Boolean isHidden;

    // 음식 판매 가능 여부
    private Boolean isSale;

    // 가게에 속한 음식 등록
    public static Food createFoods(
            FoodRequestDto foodRequestDto,
            Restaurant restaurant,
            String createdBy
    ){
        Food food = Food.builder()
                .restaurant(restaurant)
                .foodInfoTitle(foodRequestDto.getFoodInfoTitle())
                .foodInfoDesc(foodRequestDto.getFoodInfoDesc())
                .foodInfoPrice(foodRequestDto.getFoodInfoPrice())
                .isHidden(Boolean.FALSE)
                .isSale(Boolean.TRUE)
                .build();

        food.setCreatedBy(createdBy);

        return food;
    }
}
