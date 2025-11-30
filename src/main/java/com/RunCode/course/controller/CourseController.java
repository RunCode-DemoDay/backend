package com.RunCode.course.controller;

import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.dto.ArchivingSummaryResponse;
import com.RunCode.archiving.service.ArchivingService;
import com.RunCode.course.dto.CourseListResponse;
import com.RunCode.course.service.CourseService;
import com.RunCode.common.domain.ApiResponse;
import com.RunCode.course.dto.CourseDetailResponse;
import com.RunCode.course.dto.CourseSimpleResponse;
import com.RunCode.course.service.CourseService;
import com.RunCode.user.domain.CustomUserDetails;
import com.RunCode.user.repository.UserRepository;
import lombok.Getter;
import com.RunCode.review.dto.ReviewStatusResponse;
import com.RunCode.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final ArchivingService archivingService;
    private final ReviewService reviewService;
    private final CourseService courseService;

    // course별 내 archiving 전체 조회
    @GetMapping("/{courseId}/archivings")
    public ResponseEntity<ApiResponse> readArchiving(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId
    ){
        /*login 연동*/
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();
        List<ArchivingSummaryResponse> response = archivingService.readAllArchivingByCourse(courseId, userId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "코스별 archiving 전체조회 성공", response) );
    }

    // course별 review 작성여부
    @GetMapping("/{courseId}/reviews/me/status")
    public ResponseEntity<ApiResponse>  getReviewStatus(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long courseId
    ){
        //Long userId = 1L;
        /*login 연동*/
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();

        ReviewStatusResponse response = reviewService.hasUserReviewedCourse(courseId, userId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "review 작성여부 조회 성공", response));
    }

    // Course 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse> getCourseList(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(required = false) String tag, @RequestParam String order){ // tag 선택, order 필수
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();

        List<CourseListResponse> courseList = courseService.getCoursesByTagAndOrder(tag, order, userId); // 유저 아이디 상수값
        return ResponseEntity.ok(new ApiResponse(true, 200, "Course 목록 조회 성공", courseList));
    }

    // Course 검색 결과 조회
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchCourses(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam(required = false) String query, @RequestParam String order){
        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }
        Long userId = userDetails.getUserId();
        
        //log.info(">>> [COURSE SEARCH] keyword={}, userId={}", query, user != null ? user.getId() : null);

        List<CourseListResponse> courseList = courseService.searchCoursesByQueryAndOrder(query, order, userId);
        //log.info("course 검색결과: {}", courseList);
        log.info(">>> [COURSE SEARCH] keyword={}, userId={}", query, userId);
        log.info(">>> [COURSE SEARCH RESULT] size={}, result={}", courseList.size(), courseList);

        if (courseList.isEmpty()) {
            // 404 Not Found 응답 -> 검색어는 틀리는 경우가 많을 것 같아서 명시적으로 일단 넣었습니다
            return ResponseEntity
                    .status(404)
                    .body(new ApiResponse(false, 404, "해당 검색어에 맞는 코스를 찾을 수 없습니다.", null));
        }
        return ResponseEntity.ok(new ApiResponse(true, 200, "Course 검색 결과 조회 성공", courseList));
    }

    // 코스 상세 조회
    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse> getCourseDetail(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long courseId){

        if (userDetails == null) {
            return ResponseEntity
                    .status(401)
                    .body(new ApiResponse(false, 401, "로그인이 필요합니다.", null));
        }

        Long userId = userDetails.getUserId();

        CourseDetailResponse response = courseService.getCourseDetail(courseId, userId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "코스 상세 조회 성공", response));
    }

    // 코스 간단 조회
    @GetMapping("/{courseId}/summary")
    public ResponseEntity<ApiResponse> getCourseSummary(@PathVariable Long courseId){

        CourseSimpleResponse response = courseService.getCourseSummary(courseId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "코스 간단 조회 성공", response));
    }
}
