package com.RunCode.bookmark.repository;

import com.RunCode.bookmark.domain.Bookmark;
import com.RunCode.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> { }