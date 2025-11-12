package com.RunCode.user.service;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.course.domain.Course;
import com.RunCode.course.repository.CourseRepository;
import com.RunCode.login.config.jwt.TokenProvider;
import com.RunCode.review.domain.Review;
import com.RunCode.review.repository.ReviewRepository;
import com.RunCode.user.domain.CustomUserDetails;
import com.RunCode.user.dto.ReviewListResponse;
import com.RunCode.user.dto.UnreviewedCourseResponse;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.type.domain.Type;
import com.RunCode.type.repository.TypeRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final TypeRepository typeRepository;
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;

    /** 인증된 userId 가져오기 */
    public Long getRequiredUserId(CustomUserDetails userDetails) {
        if (userDetails == null) {
            throw new EntityNotFoundException("로그인이 필요합니다.");
        }
        return userDetails.getUserId();
    }
    /** 현재 로그인 사용자 정보 조회 */
    @Transactional(readOnly = true)
    public UserRegisterResponse getUserInfoById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

        String typeName = (user.getType() != null) ? user.getType().getName() : null;

        return new UserRegisterResponse(
                user.getId(),
                user.getKakaoId(),
                user.getName(),
                user.getNickname(),
                user.getProfileImage(),
                typeName
        );
    }
    /** 러너 유형 변경 */
    @Transactional
    public UserRegisterResponse updateRunnerTypeByCode(Long userId, String typeCode) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

        Long typeId = mapTypeCodeToId(typeCode);

        Type newType = typeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 type ID입니다: " + typeId));

        user.updateType(newType);
        userRepository.save(user);

        return new UserRegisterResponse(
                user.getId(),
                user.getKakaoId(),
                user.getName(),
                user.getNickname(),
                user.getProfileImage(),
                user.getType().getName()
        );
    }
    private Long mapTypeCodeToId(String code) {
        if (code == null || code.length() != 4) {
            throw new IllegalArgumentException("typeCode 형식이 잘못되었습니다. (예: GPSM)");
        }

        return switch (code.toUpperCase()) {
            case "GPSM" -> 1L;  // 새벽 솔로 도전자
            case "GPST" -> 2L;  // 아침 팀 마라토너
            case "GPNM" -> 3L;  // 야간 기록 추격자
            case "GPNT" -> 4L;  // 저녁 러닝 클럽 리더
            case "GFSM" -> 5L;  // 즉흥 새벽 질주러
            case "GFST" -> 6L;  // 팀과 함께 즐기는 아침 스프린터
            case "GFNM" -> 7L;  // 퇴근 후 기록 도전자
            case "GFNT" -> 8L;  // 야간 즉흥 러닝 메이트
            case "HPSM" -> 9L;  // 루틴형 아침 힐링러
            case "HPST" -> 10L; // 아침 공원 러닝 메이트
            case "HPNM" -> 11L; // 저녁 루틴 산책러
            case "HPNT" -> 12L; // 퇴근 후 팀 러너
            case "HFSM" -> 13L; // 기분파 아침 러너
            case "HFST" -> 14L; // 함께하는 감성 새벽 러너
            case "HFNM" -> 15L; // 노을 감상 야간 러너
            case "HFNT" -> 16L; // 저녁 즉흥 러닝 메이트
            default -> throw new IllegalArgumentException("유효하지 않은 typeCode: " + code);
        };
    }
    @Transactional
    public UserRegisterResponse updateRunnerTypeByUserId(Long userId, Long typeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

        Type newType = typeRepository.findById(typeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid type ID: " + typeId));

        user.updateType(newType);
        userRepository.save(user);

        return new UserRegisterResponse(
                user.getId(),
                user.getKakaoId(),
                user.getName(),
                user.getNickname(),
                user.getProfileImage(),
                user.getType().getName()
        );
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
    public List<UnreviewedCourseResponse> getUnreviewedCourses(Long userId) {
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

    public List<ReviewListResponse> getUserReviews(Long userId) {
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
