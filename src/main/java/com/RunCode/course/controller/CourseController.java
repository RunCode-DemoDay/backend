package com.RunCode.course.controller;

import com.RunCode.archiving.dto.ArchivingDetailResponse;
import com.RunCode.archiving.dto.ArchivingSummaryResponse;
import com.RunCode.archiving.service.ArchivingService;
import com.RunCode.course.dto.CourseListResponse;
import com.RunCode.course.service.CourseService;
import com.RunCode.common.domain.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Course 목록 조회
    @GetMapping
    public ResponseEntity<ApiResponse> getCourseList(@RequestParam(required = false) String tag, @RequestParam String order){ // tag 선택, order 필수
        List<CourseListResponse> courseList = courseService.getCoursesByTagAndOrder(tag, order, 1L); // 유저 아이디 상수값
        return ResponseEntity.ok(new ApiResponse(true, 200, "Course 목록 조회 성공", courseList));
    }
}
