package com.RunCode.bookmark.repository;

import com.RunCode.bookmark.domain.Bookmark;
import com.RunCode.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    // 특정 사용자 ID와 코스 ID에 해당하는 북마크 존재 여부를 확인
    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
}