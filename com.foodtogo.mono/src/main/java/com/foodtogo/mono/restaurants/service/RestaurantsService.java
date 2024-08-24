package com.foodtogo.mono.restaurants.service;

import com.foodtogo.mono.restaurants.core.domain.Restaurants;
import com.foodtogo.mono.restaurants.dto.RestaurantsRequestDto;
import com.foodtogo.mono.restaurants.dto.RestaurantsResponseDto;
import com.foodtogo.mono.restaurants.repository.RestaurantsRepository;
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
public class RestaurantsService {

    private final RestaurantsRepository restaurantsRepository;

    // 가게 등록
    @Transactional
    public RestaurantsResponseDto createRestaurants(
            RestaurantsRequestDto restaurantsRequestDto,
            String userId
    ){
        Restaurants restaurants = Restaurants.createRestaurants(restaurantsRequestDto,userId);
        Restaurants savedRestaurants = restaurantsRepository.save(restaurants);
        return toResponseDto(savedRestaurants);
    }

    // 가게 단건 조회
    @Transactional
    public RestaurantsResponseDto getRestaurantsById(
            UUID restaurantId
    ){
        Restaurants restaurants = restaurantsRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 식당은 없습니다."));
        return toResponseDto(restaurants);
    }

    // 가게 전체 조회
    public Page<Restaurants> getAllRestaurants(
            Pageable pageable
    ){
        return restaurantsRepository.findAll(pageable);
    }

    // 가게 정보 수정
    @Transactional
    public RestaurantsResponseDto updateRestaurants(
            UUID restaurantId,
            RestaurantsRequestDto restaurantsRequestDto,
            String userId
    ){
        Restaurants restaurants = restaurantsRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 식당은 없습니다."));
        restaurants.updateRestaurants(
                restaurantsRequestDto.getRestaurantName()
                ,restaurantsRequestDto.getRestaurantAddress()
                ,restaurantsRequestDto.getRestaurantPhoneNumber()
                ,restaurantsRequestDto.getRestaurantIntroduce()
                ,restaurantsRequestDto.getRestaurantImageUrl()
        );

        restaurants.setUpdatedBy(userId);

        Restaurants updatedRestaurants = restaurantsRepository.save(restaurants);
        return toResponseDto(updatedRestaurants);
    }

    // 가게 삭제
    @Transactional
    public void deleteRestaurants(
            UUID restaurantId,
            String userId
    ){
        Restaurants restaurants = restaurantsRepository.findById(restaurantId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이미 삭제 되었거나, 가게가 없습니다."));
        restaurants.deleteRestaurants(userId);
        restaurantsRepository.save(restaurants);
    }

    // 가게 검색
    public Page<Restaurants> searchRestaurants(
            String query,
            Pageable pageable
    ){
        return restaurantsRepository.findByRestaurantNameContaining(query, pageable);
    }

    private RestaurantsResponseDto toResponseDto(
            Restaurants restaurants
    ){
        return new RestaurantsResponseDto(
                restaurants.getRestaurantId()
                ,restaurants.getArea().name()
                ,restaurants.getIsOpened()
                ,restaurants.getRestaurantName()
                ,restaurants.getRestaurantAddress()
                ,restaurants.getRestaurantPhoneNumber()
                ,restaurants.getRestaurantIntroduce()
                ,restaurants.getRestaurantImageUrl()
        );
    }


}
