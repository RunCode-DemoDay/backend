package com.RunCode.login.service;

import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.login.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LogoutService {

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * 주어진 refresh 또는 Authorization 헤더(Bearer access)로 userId를 식별해
     * 해당 사용자의 refresh 토큰들을 DB에서 무효화한다.
     * @param refreshFromCookieOrBody  쿠키/바디에서 온 refresh 토큰(없을 수 있음)
     * @param authorizationHeader      Authorization 헤더(없을 수 있음)
     * @return true = 무효화 수행됨 / false = 무효화할 사용자 식별 실패
     */
    public boolean logoutAndInvalidate(String refreshFromCookieOrBody, String authorizationHeader) {
        Long userId = resolveUserId(refreshFromCookieOrBody, authorizationHeader);
        if (userId == null) return false;

        refreshTokenRepository.deleteByUserId(userId);
        return true;
    }

    private Long resolveUserId(String refreshOrNull, String authorizationOrNull) {
        if (refreshOrNull != null && !refreshOrNull.isBlank() && tokenProvider.validToken(refreshOrNull)) {
            return tokenProvider.getUserId(refreshOrNull);
        }
        if (authorizationOrNull != null && authorizationOrNull.startsWith("Bearer ")) {
            String access = authorizationOrNull.substring("Bearer ".length());
            if (tokenProvider.validToken(access)) {
                return tokenProvider.getUserId(access);
            }
        }
        return null;
    }
}
