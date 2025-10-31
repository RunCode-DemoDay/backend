package com.RunCode.review.service;

import com.RunCode.course.domain.Course;
import com.RunCode.course.repository.CourseRepository;
import com.RunCode.review.domain.Review;
import com.RunCode.review.dto.CourseReviewListResponse;
import com.RunCode.review.dto.ReviewItemResponse;
import com.RunCode.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    public CourseReviewListResponse getCourseReviews(Long courseId, String order) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 코스를 찾을 수 없습니다."));

        Sort sort = toSort(order);

        List<Review> reviews = reviewRepository.findByCourseWithUser(course, sort);

        List<ReviewItemResponse> reviewItems = reviews.stream()
                .map(ReviewItemResponse::of)
                .toList();

        return CourseReviewListResponse.of(course, reviewItems);
    }

    private Sort toSort(String order) {
        if (order == null || order.isBlank() || order.equalsIgnoreCase("latest")) {
            return Sort.by(Sort.Direction.DESC, "date");  // 최신순
        }
        if (order.equalsIgnoreCase("oldest")) {
            return Sort.by(Sort.Direction.ASC, "date");   // 옛날순
        }
        return Sort.by(Sort.Direction.DESC, "date");      // 기본 최신순 - 파라미터 고정이어서 필요없긴 함(예외처리)
    }
}
