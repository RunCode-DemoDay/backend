package com.RunCode.login.config.oauth;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.login.CookieUtil;
import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.login.domain.RefreshToken;
import com.RunCode.login.dto.CreateAccessTokenResponse;
import com.RunCode.login.dto.CreateTokenResponse;
import com.RunCode.login.repository.RefreshTokenRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);
    public static final String REDIRECT_PATH = "http://localhost:5174/start";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository authorizationRequestRepository;
    private final UserService userService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // OAuth2User로부터 사용자 정보 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 사용자 정보 추출
        String kakaoId = String.valueOf(oAuth2User.getAttributes().get("id"));
        Map<String, Object> properties = oAuth2User.getAttribute("properties");
        String name = (String) properties.get("nickname");
        String profileImage = (String) properties.get("profile_image");

        // 사용자 저장 또는 조회
        User user = userService.findByKakaoId(kakaoId)
                .orElseGet(() -> userService.createUser(kakaoId, name, profileImage));

        // Access Token 및 Refresh Token 생성
        String accessToken = tokenProvider.generateToken(user, ACCESS_TOKEN_DURATION);
        String refreshToken = tokenProvider.generateToken(user, REFRESH_TOKEN_DURATION);

        // Refresh Token 저장 (DB에 저장)
        saveRefreshToken(user.getId(), refreshToken);

        // Refresh Token을 쿠키에 추가 (수정된 호출)
        addRefreshTokenToCookie(request, response, refreshToken);

        // Access Token을 HTTP 헤더에 추가 (선택)
        response.addHeader("Authorization", "Bearer " + accessToken);

        // 인증 성공 후 리다이렉트
        String targetUrl = getTargetUrl(accessToken);

        clearAuthenticationAttributes(request, response);
        // ApiResponse 형태로 JSON 반환
        writeTokenResponse(response, accessToken, refreshToken);
        //getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }




    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();

        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        String cookieValue =
                "refresh_token=" + refreshToken +
                        "; Max-Age=" + cookieMaxAge +
                        "; Path=/" +
                        "; HttpOnly" +
                        "; SameSite=None; Secure";//https시 Secure
        response.addHeader("Set-Cookie", cookieValue);
        //CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private String getTargetUrl(String token) {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .queryParam("token", token)
                .build()
                .toUriString();
    }
    private void writeTokenResponse(HttpServletResponse response,
                                    String accessToken,
                                    String refreshToken) throws IOException {
        CreateTokenResponse tokenData = new CreateTokenResponse(accessToken, refreshToken);

        ApiResponse<CreateTokenResponse> apiResponse =
                new ApiResponse<>(true, 200, "로그인 성공", tokenData);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}