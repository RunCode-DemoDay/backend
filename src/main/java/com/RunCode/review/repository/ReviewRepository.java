package com.RunCode.review.repository;

import com.RunCode.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r JOIN FETCH r.course c JOIN r.user u WHERE u.id = :userId ORDER BY r.createdAt DESC")
    List<Review> findUserReviewsWithCourse(@Param("userId") Long userId);
}