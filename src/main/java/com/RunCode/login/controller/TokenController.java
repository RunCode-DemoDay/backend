package com.RunCode.login.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.login.dto.CreateAccessTokenRequest;
import com.RunCode.login.dto.CreateAccessTokenResponse;
import com.RunCode.login.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenController {
    private final TokenService tokenService;
//    @PostMapping("/auth/token/refresh")
//    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
//            @RequestBody CreateAccessTokenRequest request
//    ) {
//        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body(new CreateAccessTokenResponse(newAccessToken));
//    }
@PostMapping("/auth/token/refresh")
public ResponseEntity<ApiResponse<CreateAccessTokenResponse>> createNewAccessToken(
        @CookieValue(value = "refresh_token", required = false) String refreshFromCookie,
        @RequestBody(required = false) CreateAccessTokenRequest request
) {
    // 리프레시 토큰을 쿠키 혹은 요청 바디에서 우선적으로 가져옵니다.
    String refreshToken = (refreshFromCookie != null && !refreshFromCookie.isBlank())
            ? refreshFromCookie
            : (request != null ? request.getRefreshToken() : null);

    // 리프레시 토큰이 없으면 400 Bad Request 반환
    if (refreshToken == null || refreshToken.isBlank()) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, 400, "리프레시 토큰이 제공되지 않았습니다.", null));
    }

    try {
        // 새 액세스 토큰 생성
        String newAccessToken = tokenService.createNewAccessToken(refreshToken);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, 201, "액세스 토큰 재발급 성공",
                        new CreateAccessTokenResponse(newAccessToken)));
    } catch (IllegalArgumentException e) {
        // 리프레시 토큰 검증 실패
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(false, 401, "리프레시 토큰이 만료되었거나 유효하지 않습니다.", null));
    }
}



}