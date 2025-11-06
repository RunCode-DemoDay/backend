package com.RunCode.type.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.type.dto.TypeDataResponse;
import com.RunCode.type.service.TypeService;
import com.RunCode.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/types")
public class TypeController {

    private final TypeService typeService;

    @GetMapping
    public ResponseEntity<ApiResponse> getTypeInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }

        Long userId = userDetails.getUserId();

        TypeDataResponse responseData = typeService.getUserTypeInfo(userId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "사용자의 런비티아이 및 태그 4종 조회 성공", responseData));
    }
}