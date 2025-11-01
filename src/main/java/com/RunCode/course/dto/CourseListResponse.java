package com.RunCode.course.dto;

import com.RunCode.course.domain.Course;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseListResponse {
    private Long course_id; // Course Entity의 id -> course_id
    private String title;
    private String content;
    private Double star_average;
    private Integer review_count;
    private Double distance;
    private boolean is_bookmarked;
    private String thumbnail;

    public static CourseListResponse of(Course course, boolean isBookmarked) {
        return CourseListResponse.builder()
                .course_id(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .star_average(course.getStarAverage())
                .review_count(course.getReviewCount())
                .distance(course.getDistance())
                .is_bookmarked(isBookmarked)
                .thumbnail(course.getThumbnail())
                .build();
    }
}
