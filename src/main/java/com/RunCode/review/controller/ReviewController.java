package com.RunCode.review.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.review.dto.CourseReviewListResponse;
import com.RunCode.review.dto.ReviewCreateRequest;
import com.RunCode.review.dto.ReviewCreateResponse;
import com.RunCode.review.dto.ReviewDeleteResponse;
import com.RunCode.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class ReviewController {

    private final ReviewService reviewService;

    // 코스에 달린 리뷰 목록 조회
    @GetMapping("/{courseId}/reviews")
    public ResponseEntity<ApiResponse> getCourseReviews(@PathVariable Long courseId, @RequestParam(name = "order", defaultValue = "latest") String order) {

        CourseReviewListResponse response = reviewService.getCourseReviews(courseId, order);
        return ResponseEntity.ok(new ApiResponse(true, 200, "리뷰 목록 조회 성공", response));
    }

    // 리뷰 생성
    @PostMapping("/{courseId}/reviews")
    public ResponseEntity<ApiResponse> createReview(@PathVariable Long courseId, @RequestBody ReviewCreateRequest request) {

        Long userId = 1L;
        ReviewCreateResponse response = reviewService.createReview(courseId, userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, 201, "리뷰 생성 성공", response));
    }

    //리뷰 삭제
    @DeleteMapping("/{courseId}/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long courseId, @PathVariable Long reviewId) {

        Long userId = 1L;
        ReviewDeleteResponse response = reviewService.deleteReview(courseId, reviewId, userId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "리뷰 삭제 성공", response));
    }
}
