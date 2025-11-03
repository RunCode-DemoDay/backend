package com.RunCode.user.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.user.domain.CustomUserDetails;
import com.RunCode.user.dto.UpdateRunnerTypeRequest;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /** 현재 로그인 사용자 정보 조회 */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        Long userId = principal.getUserId();
        return userService.getUserInfoById(userId);
    }

    /** 러너 유형 변경 */
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> updateRunnerType(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody UpdateRunnerTypeRequest req
    ) {
        Long userId = principal.getUserId();
        return userService.updateRunnerTypeByUserId(userId, req.getTypeId());
    }
}