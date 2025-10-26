package com.RunCode.user.controller;

import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 현재 로그인된 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String authHeader) {
        return userService.getUserInfo(authHeader);
    }
    // 현재 로그인된 사용자의 러너 유형 변경
    @PatchMapping("/me")
    public ResponseEntity<?> updateRunnerType(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("typeId") Long typeId
    ) {
        return userService.updateRunnerType(authHeader, typeId);
    }
}