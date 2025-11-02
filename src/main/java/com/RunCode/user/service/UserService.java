package com.RunCode.user.service;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.course.domain.Course;
import com.RunCode.course.repository.CourseRepository;
import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.review.domain.Review;
import com.RunCode.review.repository.ReviewRepository;
import com.RunCode.user.dto.ReviewListResponse;
import com.RunCode.user.dto.UnreviewedCourseResponse;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.type.domain.Type;
import com.RunCode.type.repository.TypeRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final TypeRepository typeRepository;
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;

    public ResponseEntity<ApiResponse<UserRegisterResponse>> getUserInfo(String authHeader) {
        User user = getAuthenticatedUser(authHeader);
        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(false, 401, "인증이 필요합니다."));
        }

        // Lazy 로딩 문제 해결: 연관된 Type 데이터를 미리 로드
        String typeName = null;
        if (user.getType() != null) {
            Type userType = typeRepository.findById(user.getType().getId())
                    .orElse(null); // 명시적으로 타입 데이터를 초기화
            if (userType != null) {
                typeName = userType.getName();
            }
        }

        UserRegisterResponse userResponse = new UserRegisterResponse(
                user.getId(),
                user.getKakaoId(),
                user.getName(),
                user.getNickname(),
                user.getProfileImage(),
                typeName
        );
        return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "사용자 정보 조회 성공", userResponse)
        );
    }


    public User getAuthenticatedUser(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer ".length());
            if (!tokenProvider.validToken(token)) return null;
            Long userId = tokenProvider.getUserId(token);
            return userRepository.findByIdWithType(userId).orElse(null);
        }
        return null;
    }
    public ResponseEntity<ApiResponse<UserRegisterResponse>> updateRunnerType(String authHeader, Long typeId) {
        User user = getAuthenticatedUser(authHeader);
        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse<>(false, 401, "인증이 필요합니다."));
        }
        try {
            Type newType = typeRepository.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid type ID: " + typeId));
            user.updateType(newType);
            userRepository.save(user);

            String updatedTypeName = user.getType() != null ? user.getType().getName() : null;

            UserRegisterResponse userResponse = new UserRegisterResponse(
                    user.getId(),
                    user.getKakaoId(),
                    user.getName(),
                    user.getNickname(),
                    user.getProfileImage(),
                    updatedTypeName
            );

            return ResponseEntity.ok(
                    new ApiResponse<>(true, 200, "러너 유형이 업데이트되었습니다.", userResponse)
            );
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

    // 리뷰 미작성 목록 조회
    public List<UnreviewedCourseResponse> getUnreviewedCourses(String authHeader) {

        // 인증 로직: authHeader에서 사용자 ID 추출 및 유효성 검사
        // Long userId = tokenProvider.getUserId(authHeader);
        Long userId = 1L; // 일단 임시 아이디 사용..

        if (userId == null) {
            throw new IllegalArgumentException("사용자 인증 정보가 유효하지 않습니다.");
        }

        // Repository 호출: Course 엔티티와 isBookmarked 상태 조회
        List<Object[]> results = courseRepository.findUnreviewedCourseEntitiesByUserId(userId);

        // DTO로 변환
        return results.stream()
                .map(obj -> {
                    // Object[0] = Course 엔티티, Object[1] = boolean 값
                    Course course = (Course) obj[0];
                    boolean isBookmarked = (boolean) obj[1];

                    return UnreviewedCourseResponse.of(course, isBookmarked);
                })
                .collect(Collectors.toList());
    }

    public List<ReviewListResponse> getUserReviews(String authHeader) {

        // 일단은 상수 ID 사용
        Long userId = 1L;
        if (userId == null) { // 인증 실패 예외 처리
            throw new IllegalArgumentException("사용자 인증 정보가 유효하지 않습니다.");
        }
        // 리뷰 목록 조회
        List<Review> reviews = reviewRepository.findUserReviewsWithCourse(userId);

        return reviews.stream()
                .map(ReviewListResponse::of)
                .collect(Collectors.toList());
    }

}
