package com.RunCode.login.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.login.CookieUtil;
import com.RunCode.login.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class LogoutController {

    private final LogoutService logoutService;

    @PostMapping("/auth/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            @CookieValue(value = "refresh_token", required = false) String refreshFromCookie,
            @RequestBody(required = false) Map<String, String> body,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String refreshFromBody = (body != null) ? body.get("refreshToken") : null;
        String refreshToken = (refreshFromCookie != null && !refreshFromCookie.isBlank())
                ? refreshFromCookie : refreshFromBody;

        boolean invalidated = logoutService.logoutAndInvalidate(refreshToken, authHeader);

        // 쿠키 삭제: 클라이언트 측 세션 무효화
        CookieUtil.deleteCookie(request, response, "refresh_token");

        // API 응답 구조화
        if (invalidated) {
            return ResponseEntity.ok(new ApiResponse<>(
                    true, 200, "로그아웃 되었습니다.", null
            ));
        } else {
            return ResponseEntity.ok(new ApiResponse<>(
                    true, 200, "이미 만료되었거나 식별할 수 없는 세션입니다.", null
            ));
        }
    }
}
