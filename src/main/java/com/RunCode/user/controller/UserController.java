package com.RunCode.user.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.user.domain.CustomUserDetails;
import com.RunCode.user.dto.UpdateRunnerTypeRequest;
import com.RunCode.user.dto.ReviewListResponse;
import com.RunCode.course.dto.CourseWithLocationResponse;
import com.RunCode.course.service.CourseService;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.user.dto.UnreviewedCourseResponse;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CourseService courseService;

    /** 현재 로그인 사용자 정보 조회 */
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        Long userId = userService.getRequiredUserId(principal);
        UserRegisterResponse data = userService.getUserInfoById(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "사용자 정보 조회 성공", data)
        );
    }


    /** 러너 유형 변경 */
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> updateRunnerType(
            @AuthenticationPrincipal CustomUserDetails principal,
            @RequestBody UpdateRunnerTypeRequest req
    ) {
        Long userId = userService.getRequiredUserId(principal);
        UserRegisterResponse data = userService.updateRunnerTypeByCode(userId, req.getTypeCode());

        return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "러너 유형이 업데이트되었습니다.", data)
        );
    }

    // 현재 로그인된 사용자의 리뷰 미작성 코스 목록 조회 : 일단 헤더는 필수 아닌걸로 해뒀어요
    @GetMapping("/me/courses/unreviewed")
    public ResponseEntity<ApiResponse<List<UnreviewedCourseResponse>>> getUnreviewedCourses(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse<>(false, 401, "로그인이 필요합니다.", null));
        }

        Long userId = userDetails.getUserId();
        List<UnreviewedCourseResponse> unreviewedCourses = userService.getUnreviewedCourses(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "리뷰 미작성 코스 목록 조회 성공", unreviewedCourses)
        );
    }

    @GetMapping("/me/reviews")
    public ResponseEntity<ApiResponse<List<ReviewListResponse>>> getUserReviews(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse<>(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();
        List<ReviewListResponse> reviews = userService.getUserReviews(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(true, 200, "작성한 리뷰 목록 조회 성공", reviews)
        );
    }

    @GetMapping("/me/courses/archived")
    public ResponseEntity<ApiResponse> ReadAllCoursesWithArchiving(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        //Long userId=1L;
        /*login 연동*/
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();
        List<CourseWithLocationResponse> response =  courseService.getUserArchivedCoursesWithStart(userId);

        return ResponseEntity.ok(new ApiResponse<>(true, 200, "archiving 생성된 course 목록조회 성공", response));

    }
}