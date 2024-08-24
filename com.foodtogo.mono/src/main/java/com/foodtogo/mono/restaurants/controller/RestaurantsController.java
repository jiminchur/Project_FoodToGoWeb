package com.foodtogo.mono.restaurants.controller;

import com.foodtogo.mono.restaurants.dto.RestaurantsRequestDto;
import com.foodtogo.mono.restaurants.dto.RestaurantsResponseDto;
import com.foodtogo.mono.restaurants.service.RestaurantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantsController {

    private final RestaurantsService restaurantsService;

    // 가게 등록
    @PostMapping
    public RestaurantsResponseDto createRestaurants(
            @RequestBody RestaurantsRequestDto restaurantsRequestDto
    ) {
        return restaurantsService.createRestaurants(restaurantsRequestDto);
    }

    // 가게 단건 조회
    @GetMapping("/{restaurant_id}")
    public RestaurantsResponseDto getRestaurantsById(
            @PathVariable("restaurant_id") UUID restaurantId
    ){
        return restaurantsService.getRestaurantsById(restaurantId);
    }

    // 가게 정보 전체 수정
    @PutMapping("/{restaurant_id}")
    public RestaurantsResponseDto updateRestaurants(
            @PathVariable("restaurant_id") UUID restaurantId,
            @RequestBody RestaurantsRequestDto restaurantsRequestDto
    ){
        return restaurantsService.updateRestaurants(restaurantId,restaurantsRequestDto);
    }


}
