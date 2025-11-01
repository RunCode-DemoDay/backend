package com.RunCode.course.dto;

// Course 요약정보 - review 상단, archiving 상세, archiving 지도에 사용

import com.RunCode.course.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class CourseSimpleResponse {
    Long course_id;
    String title;
    String thumbnail;
    String content;
    Double start_average;
    int review_count;
    Double distance;

    public static CourseSimpleResponse of (Course course){
        return CourseSimpleResponse.builder()
                .course_id(course.getId())
                .title(course.getTitle())
                .thumbnail(course.getThumbnail())
                .content(course.getContent())
                .start_average(course.getStarAverage())
                .review_count(course.getReviewCount())
                .distance(course.getDistance())
                .build();
    }
}
