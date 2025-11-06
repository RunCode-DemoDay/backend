package com.RunCode.review.service;

import com.RunCode.course.domain.Course;
import com.RunCode.course.repository.CourseRepository;
import com.RunCode.review.domain.Review;
import com.RunCode.review.dto.*;
import com.RunCode.review.repository.ReviewRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import com.RunCode.review.dto.ReviewStatusResponse;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    // 코스에 달린 리뷰 목록 조회
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

    // 리뷰 생성
    @Transactional
    public ReviewCreateResponse createReview(Long courseId, Long userId, ReviewCreateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다."));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 코스를 찾을 수 없습니다."));

        if (reviewRepository.existsByUserAndCourse(user, course)) {
            throw new IllegalStateException("이미 이 코스에 리뷰를 작성했습니다.");
        }
        Review review = request.toEntity(user, course);
        Review savedReview = reviewRepository.save(review);

        return ReviewCreateResponse.of(savedReview);
    }

    // 리뷰 삭제
    @Transactional
    public ReviewDeleteResponse deleteReview(Long courseId, Long reviewId, Long userId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));

        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalStateException("해당 리뷰를 삭제할 권한이 없습니다.");
        }
        ReviewDeleteResponse response = ReviewDeleteResponse.of(review);

        Course course = review.getCourse();
        course.deleteReview(review.getStar());

        reviewRepository.delete(review);

        return response;
    }

    @Transactional(readOnly = true)
    public ReviewStatusResponse hasUserReviewedCourse(Long courseId, Long userId) {
        boolean reviewed = reviewRepository.existsByCourse_IdAndUser_Id(courseId, userId);

        return new ReviewStatusResponse(courseId, userId, reviewed);
    }

    private Sort toSort(String order) {
        String effectiveOrder = (order == null || order.isBlank()) ? "latest" : order;

        switch (effectiveOrder.toLowerCase()) {
            case "oldest": // 오래된 순
                return Sort.by(Sort.Direction.ASC, "createdAt");

            case "star_desc": // 별점 높은 순
                return Sort.by(Sort.Direction.DESC, "star");

            case "star_asc": // 별점 낮은 순
                return Sort.by(Sort.Direction.ASC, "star");

            case "latest": // 최신 순
            default:
                return Sort.by(Sort.Direction.DESC, "createdAt");
        }

    }
}
