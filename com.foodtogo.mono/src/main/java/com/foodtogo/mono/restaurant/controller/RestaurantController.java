package com.foodtogo.mono.restaurant.controller;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.restaurant.dto.RestaurantRequestDto;
import com.foodtogo.mono.restaurant.dto.RestaurantResponseDto;
import com.foodtogo.mono.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/restaurants")
@RequiredArgsConstructor
@Slf4j
public class RestaurantController {

    private final RestaurantService restaurantService;

    // 가게 등록
    @PostMapping
    public RestaurantResponseDto createRestaurants(
            @RequestBody RestaurantRequestDto restaurantRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestHeader(value = "X-Role", required = true) String role) {

        validateRole(role);
        log.info("role 검증 완료 및 가게 생성 role : {}, userId : {}", role, userId);
        return restaurantService.createRestaurants(restaurantRequestDto, userId);
    }

    // 가게 단건 조회
    @GetMapping("/{restaurant_id}")
    public RestaurantResponseDto getRestaurantsById(
            @PathVariable("restaurant_id") UUID restaurantId) {

        log.info("가게 단건 조회 restaurantId : {}", restaurantId);
        return restaurantService.getRestaurantsById(restaurantId);
    }

    // 가게 정보 전체 수정
    @PutMapping("/{restaurant_id}")
    public RestaurantResponseDto updateRestaurants(
            @PathVariable("restaurant_id") UUID restaurantId,
            @RequestBody RestaurantRequestDto restaurantRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestHeader(value = "X-Role", required = true) String role) {

        validateRole(role);
        log.info("role 검증 완료 및 가게 정보 수정 restaurantId: {} by userId: {}", restaurantId, userId);
        return restaurantService.updateRestaurants(restaurantId, restaurantRequestDto, userId, role);
    }

    // 가게 전체 조회
    @GetMapping
    public Page<Restaurant> getRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        log.info("가게 전체 조회, page: {}, size: {}", page, size);
        return restaurantService.getAllRestaurants(pageable);
    }

    // 가게 삭제
    @DeleteMapping("/{restaurant_id}")
    public void deleteRestaurants(
            @PathVariable("restaurant_id") UUID restaurantId,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestHeader(value = "X-Role", required = true) String role) {

        validateRole(role);
        log.info("role 검증 완료 및 가게 삭제 restaurantId: {} by userId: {}", restaurantId, userId);
        restaurantService.deleteRestaurants(restaurantId, userId, role);
    }

    // 가게 검색
    @GetMapping("/search")
    public ResponseEntity<Page<RestaurantResponseDto>> searchRestaurants(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {

        Page<RestaurantResponseDto> searchedRestaurants = restaurantService.searchRestaurants(
                query,
                page,
                size,
                sortBy,
                isAsc
        );
        log.info("가게 검색: {}, page: {}, size: {}", query, page, size);
        return new ResponseEntity<>(searchedRestaurants, HttpStatus.OK);
    }

    // 가게 운영상태 변경
    @PatchMapping("/{restaurant_id}/status")
    public void closeOpenStatus(
            @PathVariable("restaurant_id") UUID restaurantId,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestHeader(value = "X-Role", required = true) String role) {

        validateRole(role);
        log.info("가게 운영상태 변경 restaurantId: {} by userId: {}", restaurantId, userId);
        restaurantService.closeOpenStatus(restaurantId, userId, role);
    }

    // role 검증
    private void validateRole(String role) {
        if (!"MASTER".equals(role) && !"OWNER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER or OWNER.");
        }
    }
}
