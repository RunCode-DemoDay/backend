package com.RunCode.review.dto;

import com.RunCode.review.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewItemResponse {

    private Long reviewId;
    private String name;
    private String profileImage;
    private Double star;
    private String content;
    private LocalDate createdAt;

    public static ReviewItemResponse of(Review review) {
        return ReviewItemResponse.builder()
                .reviewId(review.getId())
                .name(review.getUser().getName())
                .profileImage(review.getUser().getProfileImage())
                .star(review.getStar())
                .content(review.getContent())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();
    }
}
