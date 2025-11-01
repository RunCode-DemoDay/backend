package com.RunCode.course.controller;

import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.dto.ArchivingSummaryResponse;
import com.RunCode.archiving.service.ArchivingService;
import com.RunCode.common.domain.ApiResponse;
import com.RunCode.course.dto.CourseDetailResponse;
import com.RunCode.course.dto.CourseSimpleResponse;
import com.RunCode.course.service.CourseService;
import lombok.Getter;
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

    private final CourseService courseService;

    // course별 내 archiving 전체 조회
    @GetMapping("/{courseId}/archivings")
    public ResponseEntity<ApiResponse> readArchiving(@PathVariable Long courseId){
        List<ArchivingSummaryResponse> response = archivingService.readAllArchivingByCourse(courseId, 1L);
        return ResponseEntity.ok(new ApiResponse(true, 200, "archiving 상세조회 성공", response) );
    }

    // 코스 상세 조회
    @GetMapping("/{courseId}")
    public ResponseEntity<ApiResponse> getCourseDetail(@PathVariable Long courseId){

        Long userId=1L;
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
