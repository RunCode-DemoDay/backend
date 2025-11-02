package com.RunCode.review.dto;

import com.RunCode.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateResponse {

    private Long reviewId;
    private Long courseId;

    public static ReviewCreateResponse of(Review review) {

        return ReviewCreateResponse.builder()
                .reviewId(review.getId())
                .courseId(review.getCourse().getId())
                .build();
    }
}
