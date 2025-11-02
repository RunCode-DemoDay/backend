package com.RunCode.user.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.user.dto.ReviewListResponse;
import com.RunCode.course.dto.CourseWithLocationResponse;
import com.RunCode.course.service.CourseService;
import com.RunCode.user.dto.UserRegisterResponse;
import com.RunCode.user.dto.UnreviewedCourseResponse;
import com.RunCode.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CourseService courseService;

    // 현재 로그인된 사용자 정보 조회
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> getUserInfo(
            @RequestHeader("Authorization") String authHeader) {
        return userService.getUserInfo(authHeader);
    }

    // 현재 로그인된 사용자의 러너 유형 변경
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<UserRegisterResponse>> updateRunnerType(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody Map<String, Object> body) {
        Long typeId = Long.valueOf(body.get("type_id").toString());
        return userService.updateRunnerType(authHeader, typeId);
    }

    // 현재 로그인된 사용자의 리뷰 미작성 코스 목록 조회 : 일단 헤더는 필수 아닌걸로 해뒀어요
    @GetMapping("/me/courses/unreviewed")
    public ResponseEntity<ApiResponse<List<UnreviewedCourseResponse>>> getUnreviewedCourses(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        List<UnreviewedCourseResponse> unreviewedCourses = userService.getUnreviewedCourses(authHeader);

        return ResponseEntity.ok(
                new ApiResponse(true, 200, "리뷰 미작성 코스 목록 조회 성공", unreviewedCourses)
        );
    }

    @GetMapping("/me/reviews")
    public ResponseEntity<ApiResponse<List<ReviewListResponse>>> getUserReviews(
        @RequestHeader(value = "Authorization", required = false) String authHeader) {

        List<ReviewListResponse> reviews = userService.getUserReviews(authHeader);

        return ResponseEntity.ok(
                new ApiResponse(true, 200, "작성한 리뷰 목록 조회 성공", reviews)
        );
    }
  
    @GetMapping("/me/courses/archived")
    public ResponseEntity<ApiResponse> ReadAllCoursesWithArchiving(){
        Long userId=1L;
        List<CourseWithLocationResponse> response =  courseService.getUserArchivedCoursesWithStart(userId);

        return ResponseEntity.ok(new ApiResponse(true, 200, "archiving 생성된 course 목록조회 성공", response));

    }
}