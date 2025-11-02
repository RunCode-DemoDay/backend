package com.RunCode.type.service;

import com.RunCode.type.domain.Type;
import com.RunCode.type.dto.TypeDataResponse;
import com.RunCode.type.repository.TypeRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TypeService {

    private final TypeRepository typeRepository;
    private final UserRepository userRepository;

    /* 사용자의 런비티아이 정보 + 연관 태그 4개 반환 */
    @Transactional(readOnly = true)
    public TypeDataResponse getUserTypeInfo(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자 정보를 찾을 수 없습니다. (userId: " + userId + ")"));

        // User 엔티티에서 Type 정보 추출
        Type userType = user.getType();

        if (userType == null) {
            // 사용자는 존재하지만, type_id가 NULL (런비티아이 검사 전)
            throw new NoSuchElementException("사용자의 런비티아이 정보가 존재하지 않습니다.");
        }

        return TypeDataResponse.of(userType);
    }
}
