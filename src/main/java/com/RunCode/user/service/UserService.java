package com.RunCode.user.service;

import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.type.domain.Type;
import com.RunCode.type.repository.TypeRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final TypeRepository typeRepository;

    public ResponseEntity<?> getUserInfo(String authHeader) {
        User user = getAuthenticatedUser(authHeader);
        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new UserRegisterResponse.ErrorRes("Unauthorized or invalid token"));
        }

        String typeName = user.getType() != null ? user.getType().getName() : null;

        return ResponseEntity.ok(new UserRegisterResponse(
                user.getId(),
                user.getKakaoId(),
                user.getName(),
                user.getNickname(),
                user.getProfileImage(),
                typeName
        ));
    }
    public User getAuthenticatedUser(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            if (!tokenProvider.validToken(token)) {
                return null;
            }
            Long userId = tokenProvider.getUserId(token);
            return userRepository.findById(userId).orElse(null);
        }
        return null;
    }
    public ResponseEntity<?> updateRunnerType(String authHeader, Long typeId) {
        User user = getAuthenticatedUser(authHeader);
        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new UserRegisterResponse.ErrorRes("Unauthorized or invalid token"));
        }
        try {
            Type newType = typeRepository.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid type ID: " + typeId));
            user.updateType(newType);
            userRepository.save(user);

            String updatedTypeName = user.getType() != null ? user.getType().getName() : null;

            return ResponseEntity.ok(new UserRegisterResponse(
                    user.getId(),
                    user.getKakaoId(),
                    user.getName(),
                    user.getNickname(),
                    user.getProfileImage(),
                    updatedTypeName
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(new UserRegisterResponse.ErrorRes(e.getMessage()));
        }
    }

    // 카카오 ID를 통해 로그인 처리
    public User login(String kakaoId, String name, String profileImage) {
        return userRepository.findByKakaoId(kakaoId)
                .orElseGet(() -> createUser(kakaoId, name, profileImage)); // 없으면 사용자 생성
    }
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByKakaoId(String kakaoId) {
        return userRepository.findByKakaoId(kakaoId);
    }

    public User createUser(String kakaoId, String name, String profileImage) {

        User newUser = User.create(name, kakaoId, profileImage,null);  // 필요한 필드만 생성
        return userRepository.save(newUser);
    }

}
