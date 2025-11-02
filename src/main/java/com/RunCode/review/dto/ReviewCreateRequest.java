package com.RunCode.review.dto;

import com.RunCode.course.domain.Course;
import com.RunCode.review.domain.Review;
import com.RunCode.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateRequest {

    private Double star;
    private String content;

    public Review toEntity(User user, Course course) {

        return Review.create(user, course, this.star, this.content);
    }
}
