package com.foodtogo.mono.user.service;

import com.foodtogo.mono.user.dto.request.RoleUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Slf4j(topic = "AuthService")
@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "http://43.201.69.252:8080/auth/v1/cache/users/";
//    private static final String BASE_URL = "http://localhost:8080/auth/v1/cache/users/";
    private static final String ROLE_PATH = "/role";

    public AuthService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Redis 캐시에서 사용자 권한 조회 및 갱신
     *
     * @param userId 사용자 ID
     * @return 권한명
     */
    public String getRedisUserRole(UUID userId) {
        String url = BASE_URL + userId + ROLE_PATH;
        log.info("Fetching user role from Redis cache for userId: {}", userId);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            log.info("Received response: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                log.error("Failed to fetch user role: HTTP Status {}", response.getStatusCode());
                return null;
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error fetching user role from Redis cache: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            log.error("Unexpected error fetching user role: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Redis 캐시 업데이트 (사용자 권한, 정보 업데이트)
     *
     * @param userId 사용자 ID
     * @param role   권한명
     * @return 업데이트된 권한명
     */
    public String updateRedisUserRole(UUID userId, String role) {
        String url = BASE_URL + userId + ROLE_PATH;
        log.info("Updating user role in Redis cache for userId: {} with role: {}", userId, role);

        try {
            RoleUpdateRequest requestBody = new RoleUpdateRequest(role);


            // Content-Type을 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<RoleUpdateRequest> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            log.info("Received response: {}", response.getBody());

            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            } else {
                log.error("Failed to update user role: HTTP Status {}", response.getStatusCode());
                return null;
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error updating user role in Redis cache: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            log.error("Unexpected error updating user role: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Redis 캐시 삭제 (사용자 권한, 정보 삭제)
     *
     * @param userId 사용자 ID
     * @return 성공 메시지
     */
    public void deleteRedisUserRole(UUID userId) {
        String url = BASE_URL + userId + ROLE_PATH;
        log.info("Deleting user role from Redis cache for userId: {}", userId);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.DELETE, null, String.class);
            log.info("Received response: {}", response.getBody());

            if (response.getStatusCode() != HttpStatus.OK) {
                log.error("Failed to delete user role: HTTP Status {}", response.getStatusCode());
            }

        } catch (HttpStatusCodeException e) {
            log.error("Error deleting user role from Redis cache: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("Unexpected error deleting user role: {}", e.getMessage());
        }
    }
}