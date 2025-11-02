package com.RunCode.user.dto;

import com.RunCode.course.domain.Course;
import com.RunCode.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewListResponse {
    private Long review_id;
    private Long course_id;
    private String course_title;
    private String course_thumbnail;
    private double rating; // 사용자가 매긴 별점
    private String content;
    private String review_date;
    private double course_star_average;
    private int course_review_count;
    private double course_distance;

    public static ReviewListResponse of(Review review) {
        // Review 엔티티에서 Course 정보 추출
        Course course = review.getCourse();

        return ReviewListResponse.builder()
                .review_id(review.getId())
                .course_id(course.getId())
                .course_title(course.getTitle())
                .course_thumbnail(course.getThumbnail())
                .rating(review.getStar()) // Review 엔티티의 star 필드가 rating에 해당
                .content(review.getContent())
                .review_date(review.getReviewDate())
                .course_star_average(course.getStarAverage())
                .course_review_count(course.getReviewCount())
                .course_distance(course.getDistance())
                .build();
    }
}
