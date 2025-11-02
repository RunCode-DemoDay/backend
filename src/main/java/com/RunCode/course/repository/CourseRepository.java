package com.RunCode.course.repository;

import com.RunCode.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    // 로그인한 사용자가 아카이빙은 했지만 리뷰를 아직 작성하지 않은 리뷰 미작성 코스 목록 조회
    @Query(value = "SELECT c, CASE WHEN b.id IS NULL THEN FALSE ELSE TRUE END " + // 북마크 존재 여부를 boolean으로 반환
            "FROM Archiving a " +
            "JOIN a.course c " +
            "LEFT JOIN Bookmark b ON b.course.id = c.id AND b.user.id = a.user.id " +
            "WHERE a.user.id = :userId " +
            "AND NOT EXISTS (" +
            "    SELECT 1 FROM Review r " +
            "    WHERE r.course.id = c.id AND r.user.id = a.user.id" +
            ")")
    List<Object[]> findUnreviewedCourseEntitiesByUserId(@Param("userId") Long userId);
}
