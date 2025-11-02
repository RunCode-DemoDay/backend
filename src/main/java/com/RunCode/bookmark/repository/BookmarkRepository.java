package com.RunCode.bookmark.repository;

import com.RunCode.bookmark.domain.Bookmark;
import com.RunCode.course.domain.Course;
import com.RunCode.user.domain.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // 특정 사용자 ID와 코스 ID에 해당하는 북마크 존재 여부를 확인
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);

    Optional<Bookmark> findByUserAndCourse(User user, Course course);

    @Query("""
            SELECT b FROM Bookmark b
            JOIN FETCH b.course 
            WHERE b.user = :user
            """)
    List<Bookmark> findByUserwithCourse(@Param("user") User user, Sort sort);

    boolean existsByUserAndCourse(User user, Course course);
}
