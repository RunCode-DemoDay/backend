package com.RunCode.review.dto;

import com.RunCode.course.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseReviewListResponse {

    private Long courseId;
    private Double starAverage;
    private Integer reviewCount;
    private List<ReviewItemResponse> reviews;

    public static CourseReviewListResponse of(Course course, List<ReviewItemResponse> reviews) {

        return CourseReviewListResponse.builder()
                .courseId(course.getId())
                .starAverage(course.getStarAverage())
                .reviewCount(course.getReviewCount())
                .reviews(reviews)
                .build();
    }
}
