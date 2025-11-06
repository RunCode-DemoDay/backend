package com.RunCode.review.repository;

import com.RunCode.course.domain.Course;
import com.RunCode.review.domain.Review;
import com.RunCode.user.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r JOIN FETCH r.course c JOIN r.user u WHERE u.id = :userId ORDER BY r.createdAt DESC")
    List<Review> findUserReviewsWithCourse(@Param("userId") Long userId);

    @Query("""
            SELECT r FROM Review r 
            JOIN FETCH r.user
            WHERE r.course = :course
            """)
    List<Review> findByCourseWithUser(@Param("course") Course course, Sort sort);

    boolean existsByUserAndCourse(User user, Course course);

    // 사용자가 코스에 대한 리뷰를 작성한 적이 있는지
    boolean existsByCourse_IdAndUser_Id(Long course_id,Long userId);
}
