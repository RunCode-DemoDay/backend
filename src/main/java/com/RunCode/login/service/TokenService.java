package com.RunCode.login.service;

import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.user.domain.User;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;

    public String createNewAccessToken(String refreshToken){
        //토큰 유효성 검사에 실패하면 예외 처리
        if(!tokenProvider.validToken(refreshToken)){
            throw new IllegalArgumentException("Unexpected token");
        }
        Long userId = refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user = userService.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with ID: " + userId)
        );

        return tokenProvider.generateToken(user, Duration.ofHours(2));
    }

}