package com.RunCode.user.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 현재 로그인된 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> getUserInfo(
            @RequestHeader("Authorization") String authHeader) {
        return userService.getUserInfo(authHeader);
    }

    // 현재 로그인된 사용자의 러너 유형 변경
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> updateRunnerType(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> body) {
        Long typeId = Long.valueOf(body.get("type_id").toString());
        return userService.updateRunnerType(authHeader, typeId);
    }

}