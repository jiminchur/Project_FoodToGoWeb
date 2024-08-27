package com.foodtogo.mono.restaurant.controller;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.restaurant.dto.RestaurantRequestDto;
import com.foodtogo.mono.restaurant.dto.RestaurantResponseDto;
import com.foodtogo.mono.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 가게 등록
    @PostMapping
    public RestaurantResponseDto createRestaurants(
            @RequestBody RestaurantRequestDto restaurantRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ) {
        return restaurantService.createRestaurants(restaurantRequestDto,userId);
    }

    // 가게 단건 조회
    @GetMapping("/{restaurant_id}")
    public RestaurantResponseDto getRestaurantsById(
            @PathVariable("restaurant_id") UUID restaurantId
    ){
        return restaurantService.getRestaurantsById(restaurantId);
    }

    // 가게 정보 전체 수정
    @PutMapping("/{restaurant_id}")
    public RestaurantResponseDto updateRestaurants(
            @PathVariable("restaurant_id") UUID restaurantId,
            @RequestBody RestaurantRequestDto restaurantRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        return restaurantService.updateRestaurants(restaurantId, restaurantRequestDto,userId);
    }

    // 가게 전체 조회
    @GetMapping
    public Page<Restaurant> getRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.getAllRestaurants(pageable);
    }

    // 가게 삭제
    @DeleteMapping("/{restaurant_id}")
    public void deleteRestaurants(
            @PathVariable("restaurant_id") UUID restaurantId,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        restaurantService.deleteRestaurants(restaurantId,userId);
    }

    // 가게 검색
    @GetMapping("/search")
    public Page<Restaurant> getRestaurants(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return restaurantService.searchRestaurants(query, pageable);
    }

    // 가게 운영상태 변경
    @PatchMapping("/{restaurant_id}/status")
    public void closeOpenStatus(
            @PathVariable("restaurant_id") UUID restaurantId
    ){
        restaurantService.closeOpenStatus(restaurantId);
    }
}
