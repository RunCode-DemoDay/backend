package com.RunCode.review.controller;

import com.RunCode.common.domain.ApiResponse;
import com.RunCode.review.dto.CourseReviewListResponse;
import com.RunCode.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{courseId}/reviews")
    public ResponseEntity<ApiResponse> getCourseReviews(@PathVariable Long courseId, @RequestParam(name = "order", defaultValue = "latest") String order) {

        CourseReviewListResponse response = reviewService.getCourseReviews(courseId, order);

        return ResponseEntity.ok(new ApiResponse(true, 200, "리뷰 목록 조회 성공", response));
    }
}
