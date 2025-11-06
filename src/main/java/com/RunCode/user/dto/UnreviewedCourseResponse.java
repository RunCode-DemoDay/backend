package com.RunCode.user.dto;

import com.RunCode.course.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnreviewedCourseResponse {
    private Long course_id;
    private String title;
    private String content;
    private double star_average;
    private int review_count;
    private double distance;
    private boolean is_bookmarked;
    private String thumbnail;

    public static UnreviewedCourseResponse of(Course course, boolean isBookmarked) {
        return UnreviewedCourseResponse.builder()
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
