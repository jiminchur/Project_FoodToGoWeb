package com.foodtogo.mono.restaurant.service;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.restaurant.dto.RestaurantRequestDto;
import com.foodtogo.mono.restaurant.dto.RestaurantResponseDto;
import com.foodtogo.mono.restaurant.repository.RestaurantRepository;
import com.foodtogo.mono.restaurant.repository.RestaurantRepositoryCustom;
import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import com.foodtogo.mono.restaurant_category.repository.RestaurantCategoryRepository;
import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final RestaurantCategoryRepository restaurantCategoryRepository;
    private final RestaurantRepositoryCustom restaurantRepositoryCustom;

    @Transactional
    public RestaurantResponseDto createRestaurants(RestaurantRequestDto restaurantRequestDto, String userId) {
        User user = findUser(userId);
        RestaurantCategory category = findCategory(restaurantRequestDto.getCategoryId());

        Restaurant restaurant = Restaurant.createRestaurants(restaurantRequestDto, userId, user, category);
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        log.info("Restaurant created: {}", savedRestaurant.getRestaurantId());
        return toResponseDto(savedRestaurant);
    }

    @Transactional
    public RestaurantResponseDto getRestaurantsById(UUID restaurantId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        return toResponseDto(restaurant);
    }

    @Transactional
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        return restaurantRepositoryCustom.findRestaurantsWithLob(pageable);
    }

    @Transactional
    public RestaurantResponseDto updateRestaurants(UUID restaurantId, RestaurantRequestDto restaurantRequestDto, String userId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        validateUserOwnership(restaurant, userId);

        restaurant.updateRestaurants(
                restaurantRequestDto.getRestaurantName(),
                restaurantRequestDto.getRestaurantAddress(),
                restaurantRequestDto.getRestaurantPhoneNumber(),
                restaurantRequestDto.getRestaurantIntroduce(),
                restaurantRequestDto.getRestaurantImageUrl()
        );

        restaurant.setUpdatedBy(userId);
        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);

        log.info("Restaurant updated: {}", updatedRestaurant.getRestaurantId());
        return toResponseDto(updatedRestaurant);
    }

    @Transactional
    public void deleteRestaurants(UUID restaurantId, String userId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        validateUserOwnership(restaurant, userId);

        restaurant.deleteRestaurants(userId);
        restaurantRepository.save(restaurant);

        log.info("Restaurant deleted: {}", restaurantId);
    }

    public Page<Restaurant> searchRestaurants(String query, Pageable pageable) {
        return restaurantRepository.findByRestaurantNameContaining(query, pageable);
    }

    public void closeOpenStatus(UUID restaurantId, String userId) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        validateUserOwnership(restaurant, userId);

        restaurant.setIsOpened(!restaurant.getIsOpened());
        restaurantRepository.save(restaurant);

        log.info("Restaurant open status changed: {} - {}", restaurantId, restaurant.getIsOpened());
    }

    private RestaurantResponseDto toResponseDto(Restaurant restaurant) {
        return new RestaurantResponseDto(
                restaurant.getRestaurantId(),
                restaurant.getArea().name(),
                restaurant.getIsOpened(),
                restaurant.getRestaurantName(),
                restaurant.getRestaurantAddress(),
                restaurant.getRestaurantPhoneNumber(),
                restaurant.getRestaurantIntroduce(),
                restaurant.getRestaurantImageUrl()
        );
    }

    // user_id로 찾기
    private User findUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // category_id로 찾기
    private RestaurantCategory findCategory(UUID categoryId) {
        return restaurantCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    // restaurant_id로 찾기
    private Restaurant findRestaurantById(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 식당은 없습니다."));
    }

    private void validateUserOwnership(Restaurant restaurant, String userId) {
        if (!restaurant.getUser().getUserId().equals(UUID.fromString(userId))) {
            log.info("User ID validation failed: userId={}, restaurantOwnerId={}", userId, restaurant.getUser().getUserId());
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "가게 주인이 아닙니다.");
        }
    }
}
