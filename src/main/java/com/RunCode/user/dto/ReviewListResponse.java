package com.RunCode.user.dto;

import com.RunCode.course.domain.Course;
import com.RunCode.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

        String formattedDate;
        LocalDateTime createdAt = review.getCreatedAt();

        if (createdAt != null) { // DB 자체에는 자동으로 들어가게 되어있는데 더미데이터에 없어서 그런듯?
            // 명세된 "YYYY.MM.DD" 형태의 문자열로 포맷
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            formattedDate = createdAt.format(formatter);
        } else {
            formattedDate = "";
        }

        return ReviewListResponse.builder()
                .review_id(review.getId())
                .course_id(course.getId())
                .course_title(course.getTitle())
                .course_thumbnail(course.getThumbnail())
                .rating(review.getStar()) // Review 엔티티의 star 필드가 rating에 해당함!
                .content(review.getContent())
                .review_date(formattedDate)
                .course_star_average(course.getStarAverage())
                .course_review_count(course.getReviewCount())
                .course_distance(course.getDistance())
                .build();
    }
}
