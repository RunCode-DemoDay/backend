package com.RunCode.login.controller;

import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.user.domain.User;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController

public class LogintestController {

    private final TokenProvider tokenProvider;
    private final UserService userService;


    @GetMapping("/")
    public ResponseEntity<?> me(
            @RequestParam(value = "token", required = false) String token,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    ) {
        // 1) 쿼리 파라미터 token 우선 사용, 없으면 Authorization 헤더에서 추출
        if (token == null && authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring("Bearer ".length());
        }
        if (token == null || !tokenProvider.validToken(token)) {
            return ResponseEntity.status(401).body(new UserRegisterResponse.ErrorRes("Invalid or missing token"));
        }

        // 2) 토큰에서 userId 우선, 없으면 subject(kakaoId)로 조회
        Long userId = tokenProvider.getUserId(token);
        Optional<User> userOpt;
        if (userId != null) {
            userOpt = userService.findById(userId);
        } else {
            // subject = kakaoId 로 발급했으므로 여기서도 조회 가능
            String kakaoId = tokenProvider.getAuthentication(token).getName();
            userOpt = userService.findByKakaoId(kakaoId);
        }

        User u = userOpt.orElse(null);
        if (u == null) {
            return ResponseEntity.status(404).body(new UserRegisterResponse.ErrorRes("User not found"));
        }

        // 3) DTO로 응답
        String typeName = (u.getType() != null) ? u.getType().getName() : null; // null 허용 설계 시 안전 처리
        return ResponseEntity.ok(new UserRegisterResponse(
                u.getId(),
                u.getKakaoId(),
                u.getName(),
                u.getNickname(),
                u.getProfileImage(),
                typeName
        ));
    }
}
