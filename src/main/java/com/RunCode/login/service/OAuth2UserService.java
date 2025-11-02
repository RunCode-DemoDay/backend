package com.RunCode.login.service;

import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        // 기본 OAuth2User 로드
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 정보 가져오기
        String kakaoId = oAuth2User.getAttribute("id").toString();
        Map<String, Object> kakaoAccount = oAuth2User.getAttribute("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String name = (String) profile.get("nickname");
        String profileImage = (String) profile.get("profile_image_url");

        // 사용자 데이터 저장 또는 조회
        User user = userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> userService.createUser(kakaoId, name, profileImage)); // UserService의 createUser 호출

        // 커스텀 OAuth2User 생성 반환 (필요 시)
        return oAuth2User;
    }


}
