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

    Optional<Bookmark> findByUserAndCourse(User user, Course course);

    @Query("""
            SELECT b FROM Bookmark b
            JOIN FETCH b.course 
            WHERE b.user = :user
            """)
    List<Bookmark> findByUserwithCourse(@Param("user") User user, Sort sort);

    boolean existsByUserAndCourse(User user, Course course);
}
