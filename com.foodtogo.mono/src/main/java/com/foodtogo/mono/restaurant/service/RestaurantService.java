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

    // 가게 생성
    @Transactional
    public RestaurantResponseDto createRestaurants(
            RestaurantRequestDto restaurantRequestDto,
            String userId
    ) {
        User user = findUser(userId);
        RestaurantCategory category = findCategory(restaurantRequestDto.getCategoryId());

        Restaurant restaurant = new Restaurant(restaurantRequestDto, userId, user, category);

        restaurantRepository.save(restaurant);

        log.info("가게가 생성되었습니다: {}", restaurant.getRestaurantId());
        return new RestaurantResponseDto(restaurant);
    }

    // 가게 단건 조회
    @Transactional(readOnly = true)
    public RestaurantResponseDto getRestaurantsById(
            UUID restaurantId
    ) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        log.info("가게가 조회되었습니다 : {}", restaurant.getRestaurantId());
        return new RestaurantResponseDto(restaurant);
    }

    // 가게 전체 조회
    @Transactional(readOnly = true)
    public Page<Restaurant> getAllRestaurants(
            Pageable pageable
    ) {
        log.info("가게 전체 조회 시작");
        return restaurantRepositoryCustom.findRestaurants(pageable);
    }

    // 가게 수정
    @Transactional
    public RestaurantResponseDto updateRestaurants(
            UUID restaurantId,
            RestaurantRequestDto restaurantRequestDto,
            String userId,
            String role
    ) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        validateUserOwnership(restaurant, userId, role);

        restaurant.updateRestaurants(restaurantRequestDto, userId);

        log.info("가게가 수정되었습니다: {}", restaurant.getRestaurantId());

        return new RestaurantResponseDto(restaurant);
    }

    // 가게 삭제
    @Transactional
    public void deleteRestaurants(
            UUID restaurantId,
            String userId,
            String role
    ) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        validateUserOwnership(restaurant, userId, role);

        restaurant.deleteRestaurants(userId);
        restaurantRepository.save(restaurant);

        log.info("가게가 삭제되었습니다: {}", restaurantId);
    }

    // 가게 검색
    public Page<Restaurant> searchRestaurants(
            String query,
            Pageable pageable
    ) {
        return restaurantRepository.findByRestaurantNameContaining(query, pageable);
    }

    // 가게 오픈상태 토글
    public void closeOpenStatus(
            UUID restaurantId,
            String userId,
            String role
    ) {
        Restaurant restaurant = findRestaurantById(restaurantId);
        validateUserOwnership(restaurant, userId, role);

        restaurant.setIsOpened(!restaurant.getIsOpened());
        restaurantRepository.save(restaurant);

        log.info("가게 오픈 상태가 변경되었습니다: {} - {}", restaurantId, restaurant.getIsOpened());
    }

    // user_id로 찾기
    private User findUser(
            String userId
    ) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }

    // category_id로 찾기
    private RestaurantCategory findCategory(
            UUID categoryId
    ) {
        return restaurantCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다."));
    }

    // restaurant_id로 찾기
    private Restaurant findRestaurantById(
            UUID restaurantId
    ) {
        return restaurantRepository.findById(restaurantId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 식당은 존재하지 않습니다."));
    }

    // 사용자 소유권 검증
    private void validateUserOwnership(
            Restaurant restaurant,
            String userId,
            String role
    ) {
        if (!"MASTER".equals(role)) {

            log.info("해당 가게 주인인지 검증 시작");

            if (!restaurant.getUser().getUserId().equals(UUID.fromString(userId))) {
                log.info("사용자 ID 검증 실패: userId={}, restaurantOwnerId={}", userId, restaurant.getUser().getUserId());
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "가게 주인이 아닙니다.");
            }
        }
    }
}
