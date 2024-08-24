package com.foodtogo.mono.restaurants.service;

import com.foodtogo.mono.restaurants.core.Restaurants;
import com.foodtogo.mono.restaurants.dto.RestaurantsRequestDto;
import com.foodtogo.mono.restaurants.dto.RestaurantsResponseDto;
import com.foodtogo.mono.restaurants.repository.RestaurantsRepository;
import lombok.RequiredArgsConstructor;
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
            RestaurantsRequestDto restaurantsRequestDto
    ){
        Restaurants restaurants = Restaurants.createRestaurants(restaurantsRequestDto);
        Restaurants savedRestaurants = restaurantsRepository.save(restaurants);
        return toResponseDto(savedRestaurants);
    }

    // 가게 단건 조회
    public RestaurantsResponseDto getRestaurantsById(
            UUID restaurantId
    ){
        Restaurants restaurants = restaurantsRepository.findById(restaurantId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 식당은 없습니다."));
        return toResponseDto(restaurants);
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
