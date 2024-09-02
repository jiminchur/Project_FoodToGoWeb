package com.foodtogo.mono.restaurant.service;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import com.foodtogo.mono.restaurant.dto.RestaurantRequestDto;
import com.foodtogo.mono.restaurant.dto.RestaurantResponseDto;
import com.foodtogo.mono.restaurant.repository.RestaurantRepository;
import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import com.foodtogo.mono.restaurant_category.repository.RestaurantCategoryRepository;
import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // 가게 생성
    @Transactional
    public RestaurantResponseDto createRestaurants(RestaurantRequestDto restaurantRequestDto, String userId) {
        User user = findUser(userId); // 사용자 찾기
        RestaurantCategory category = findCategory(restaurantRequestDto.getCategoryId()); // 카테고리 찾기

        // 새로운 Restaurant 객체 생성
        Restaurant restaurant = new Restaurant(
                restaurantRequestDto.getRestaurantName(),
                restaurantRequestDto.getRestaurantAddress(),
                restaurantRequestDto.getRestaurantPhoneNumber(),
                restaurantRequestDto.getRestaurantIntroduce(),
                restaurantRequestDto.getRestaurantImageUrl(),
                user,
                category);
        restaurantRepository.save(restaurant); // 데이터베이스에 가게 저장

        log.info("가게가 생성되었습니다: {}", restaurant.getRestaurantId());
        return new RestaurantResponseDto(restaurant); // 생성된 가게 정보 반환
    }

    // 가게 단건 조회
    @Transactional(readOnly = true)
    public RestaurantResponseDto getRestaurantsById(UUID restaurantId) {
        Restaurant restaurant = findRestaurantById(restaurantId); // 가게 찾기
        log.info("가게가 조회되었습니다 : {}", restaurant.getRestaurantId());
        return new RestaurantResponseDto(restaurant); // 가게 정보 반환
    }

    // 가게 전체 조회
    @Transactional(readOnly = true)
    public Page<Restaurant> getAllRestaurants(Pageable pageable) {
        log.info("가게 전체 조회 시작");
        return restaurantRepository.findAllAndDeletedAtIsNull(pageable); // 삭제되지 않은 가게 목록 반환
    }

    // 가게 수정
    @Transactional
    public RestaurantResponseDto updateRestaurants(UUID restaurantId, RestaurantRequestDto restaurantRequestDto, String userId, String role) {
        Restaurant restaurant = findRestaurantById(restaurantId); // 가게 찾기
        validateUserOwnership(restaurant, userId, role); // 사용자 소유권 검증

        restaurant.updateRestaurants(
                restaurantRequestDto.getRestaurantName(),
                restaurantRequestDto.getRestaurantAddress(),
                restaurantRequestDto.getRestaurantPhoneNumber(),
                restaurantRequestDto.getRestaurantIntroduce(),
                restaurantRequestDto.getRestaurantImageUrl()); // 가게 정보 업데이트
        log.info("가게가 수정되었습니다: {}", restaurant.getRestaurantId());

        return new RestaurantResponseDto(restaurant); // 수정된 가게 정보 반환
    }

    // 가게 삭제
    @Transactional
    public void deleteRestaurants(UUID restaurantId, String userId, String role) {
        Restaurant restaurant = findRestaurantById(restaurantId); // 가게 찾기
        validateUserOwnership(restaurant, userId, role); // 사용자 소유권 검증

        restaurant.deleteRestaurants(userId); // 가게 삭제 처리
        restaurantRepository.save(restaurant); // 데이터베이스에 변경 사항 저장

        log.info("가게가 삭제되었습니다: {}", restaurantId);
    }

    // 가게 검색
    public Page<RestaurantResponseDto> searchRestaurants(
                String keyword,
                int page,
                int size,
                String sortBy
            ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        Page<Restaurant> restaurantsList = restaurantRepository.findByRestaurantNameContaining(keyword, pageable);

        return restaurantsList.map(RestaurantResponseDto::new); // 이름으로 가게 검색
    }

    // 가게 오픈상태 토글
    public void closeOpenStatus(UUID restaurantId, String userId, String role) {
        Restaurant restaurant = findRestaurantById(restaurantId); // 가게 찾기
        validateUserOwnership(restaurant, userId, role); // 사용자 소유권 검증

        restaurant.setIsOpened(!restaurant.getIsOpened()); // 오픈 상태 반전
        restaurantRepository.save(restaurant); // 데이터베이스에 변경 사항 저장

        log.info("가게 오픈 상태가 변경되었습니다: {} - {}", restaurantId, restaurant.getIsOpened());
    }

    // user_id로 사용자 찾기
    private User findUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")); // 사용자 없을 경우 예외 발생
    }

    // category_id로 카테고리 찾기
    private RestaurantCategory findCategory(UUID categoryId) {
        return restaurantCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("카테고리를 찾을 수 없습니다.")); // 카테고리 없을 경우 예외 발생
    }

    // restaurant_id로 가게 찾기
    private Restaurant findRestaurantById(UUID restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 식당은 존재하지 않습니다.")); // 가게 없을 경우 예외 발생
    }

    // 사용자 소유권 검증
    private void validateUserOwnership(Restaurant restaurant, String userId, String role) {
        if (!"MASTER".equals(role)) { // 역할이 MASTER이 아닐 경우
            log.info("해당 가게 주인인지 검증 시작");
            if (!restaurant.getUser().getUserId().equals(UUID.fromString(userId))) {
                log.info("사용자 ID 검증 실패: userId={}, restaurantOwnerId={}", userId, restaurant.getUser().getUserId());
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "가게 주인이 아닙니다."); // 주인이 아닐 경우 예외 발생
            }
        }
    }

    // 페이지 처리
    private Pageable convertToPage(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }
}