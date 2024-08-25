package com.foodtogo.mono.restaurants.controller;

import com.foodtogo.mono.restaurants.core.domain.Restaurants;
import com.foodtogo.mono.restaurants.dto.RestaurantsRequestDto;
import com.foodtogo.mono.restaurants.dto.RestaurantsResponseDto;
import com.foodtogo.mono.restaurants.service.RestaurantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            @RequestBody RestaurantsRequestDto restaurantsRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ) {
        return restaurantsService.createRestaurants(restaurantsRequestDto,userId);
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
            @RequestBody RestaurantsRequestDto restaurantsRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        return restaurantsService.updateRestaurants(restaurantId,restaurantsRequestDto,userId);
    }

    // 가게 전체 조회
    @GetMapping
    public Page<Restaurants> getRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return restaurantsService.getAllRestaurants(pageable);
    }

    // 가게 삭제
    @DeleteMapping("/{restaurant_id}")
    public void deleteRestaurants(
            @PathVariable("restaurant_id") UUID restaurantId,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        restaurantsService.deleteRestaurants(restaurantId,userId);
    }

    // 가게 검색
    @GetMapping("/search")
    public Page<Restaurants> getRestaurants(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return restaurantsService.searchRestaurants(query, pageable);
    }

    // 가게 운영상태 변경
    @PatchMapping("/{restaurant_id}/status")
    public void closeOpenStatus(
            @PathVariable("restaurant_id") UUID restaurantId
    ){
        restaurantsService.closeOpenStatus(restaurantId);
    }
}