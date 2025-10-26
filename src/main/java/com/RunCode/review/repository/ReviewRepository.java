package com.RunCode.review.repository;

import com.RunCode.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 사용자가 코스에 대한 리뷰를 작성한 적이 있는지
    boolean existsByCourse_IdAndUser_Id(Long course_id,Long userId);

}
