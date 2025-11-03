package com.RunCode.user.service;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.type.domain.Type;
import com.RunCode.type.repository.TypeRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TypeRepository typeRepository;


    @Transactional(readOnly = true)
    public ResponseEntity<ApiResponse<UserRegisterResponse>> getUserInfoById(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(false, 401, "인증이 필요합니다."));
        }

        String typeName = (user.getType() != null) ? user.getType().getName() : null;

        UserRegisterResponse data = new UserRegisterResponse(
                user.getId(),
                user.getKakaoId(),
                user.getName(),
                user.getNickname(),
                user.getProfileImage(),
                typeName
        );
        return ResponseEntity.ok(new ApiResponse<>(true, 200, "사용자 정보 조회 성공", data));
    }

    @Transactional
    public ResponseEntity<ApiResponse<UserRegisterResponse>> updateRunnerTypeByUserId(Long userId, Long typeId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(false, 401, "인증이 필요합니다."));
        }
        try {
            Type newType = typeRepository.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid type ID: " + typeId));
            user.updateType(newType);
            userRepository.save(user);

            String updatedTypeName = (user.getType() != null) ? user.getType().getName() : null;

            UserRegisterResponse data = new UserRegisterResponse(
                    user.getId(),
                    user.getKakaoId(),
                    user.getName(),
                    user.getNickname(),
                    user.getProfileImage(),
                    updatedTypeName
            );
            return ResponseEntity.ok(new ApiResponse<>(true, 200, "러너 유형이 업데이트되었습니다.", data));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse<>(false, 400, e.getMessage()));
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
