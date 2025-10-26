package com.RunCode.course.controller;

import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.dto.ArchivingSummaryResponse;
import com.RunCode.archiving.service.ArchivingService;
import com.RunCode.common.domain.ApiResponse;
import com.RunCode.review.dto.ReviewStatusResponse;
import com.RunCode.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {

    private final ArchivingService archivingService;
    private final ReviewService reviewService;

    // course별 내 archiving 전체 조회
    @GetMapping("/{courseId}/archivings")
    public ResponseEntity<ApiResponse> readArchiving(@PathVariable Long courseId){
        List<ArchivingSummaryResponse> response = archivingService.readAllArchivingByCourse(courseId, 1L);
        return ResponseEntity.ok(new ApiResponse(true, 200, "archiving 상세조회 성공", response) );
    }


    // course별 review 작성여부
    @GetMapping("/{courseId}/reviews/me/status")
    public ResponseEntity<ApiResponse>  getReviewStatus(
            @PathVariable Long courseId
    ){
        Long userId = 1L;
        ReviewStatusResponse response = reviewService.hasUserReviewedCourse(courseId, userId);
        return ResponseEntity.ok(new ApiResponse(true, 200, "review 작성여부 조회 성공", response));
    }
}
