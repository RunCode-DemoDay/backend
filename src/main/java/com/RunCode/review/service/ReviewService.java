package com.RunCode.review.service;

import com.RunCode.review.dto.ReviewStatusResponse;
import com.RunCode.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewStatusResponse hasUserReviewedCourse(Long courseId, Long userId){
        boolean reviewed = reviewRepository.existsByCourse_IdAndUser_Id(courseId, userId);

        return new ReviewStatusResponse(courseId, userId, reviewed);
    }
}
