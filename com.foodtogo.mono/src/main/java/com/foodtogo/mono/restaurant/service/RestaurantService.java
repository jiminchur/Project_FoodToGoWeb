package com.foodtogo.mono.restaurant.service;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.restaurant.dto.RestaurantRequestDto;
import com.foodtogo.mono.restaurant.dto.RestaurantResponseDto;
import com.foodtogo.mono.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    // 가게 등록
    @Transactional
    public RestaurantResponseDto createRestaurants(
            RestaurantRequestDto restaurantRequestDto,
            String userId
    ){
        Restaurant restaurant = Restaurant.createRestaurants(restaurantRequestDto,userId);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return toResponseDto(savedRestaurant);
    }

    // 가게 단건 조회
    @Transactional
    public RestaurantResponseDto getRestaurantsById(
            UUID restaurantId
    ){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 식당은 없습니다."));
        return toResponseDto(restaurant);
    }

    // 가게 전체 조회
    public Page<Restaurant> getAllRestaurants(
            Pageable pageable
    ){
        return restaurantRepository.findAll(pageable);
    }

    // 가게 정보 수정
    @Transactional
    public RestaurantResponseDto updateRestaurants(
            UUID restaurantId,
            RestaurantRequestDto restaurantRequestDto,
            String userId
    ){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 식당은 없습니다."));
        restaurant.updateRestaurants(
                restaurantRequestDto.getRestaurantName()
                , restaurantRequestDto.getRestaurantAddress()
                , restaurantRequestDto.getRestaurantPhoneNumber()
                , restaurantRequestDto.getRestaurantIntroduce()
                , restaurantRequestDto.getRestaurantImageUrl()
        );

        restaurant.setUpdatedBy(userId);

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return toResponseDto(updatedRestaurant);
    }

    // 가게 삭제
    @Transactional
    public void deleteRestaurants(
            UUID restaurantId,
            String userId
    ){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 삭제 되었거나, 가게가 없습니다."));
        restaurant.deleteRestaurants(userId);
        restaurantRepository.save(restaurant);
    }

    // 가게 검색
    public Page<Restaurant> searchRestaurants(
            String query,
            Pageable pageable
    ){
        return restaurantRepository.findByRestaurantNameContaining(query, pageable);
    }

    // 가게 운영 상태 변경
    public void closeOpenStatus(
            UUID restaurantId
    ){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 식당은 없습니다."));

        restaurant.setIsOpened(!restaurant.getIsOpened());

        restaurantRepository.save(restaurant);
    }

    private RestaurantResponseDto toResponseDto(
            Restaurant restaurant
    ){
        return new RestaurantResponseDto(
                restaurant.getRestaurantId()
                , restaurant.getArea().name()
                , restaurant.getIsOpened()
                , restaurant.getRestaurantName()
                , restaurant.getRestaurantAddress()
                , restaurant.getRestaurantPhoneNumber()
                , restaurant.getRestaurantIntroduce()
                , restaurant.getRestaurantImageUrl()
        );
    }


}
