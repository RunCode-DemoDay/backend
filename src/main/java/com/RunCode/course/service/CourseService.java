package com.RunCode.course.service;

import com.RunCode.bookmark.repository.BookmarkRepository;
import com.RunCode.bookmark.service.BookmarkService;
import com.RunCode.course.domain.Course;
import com.RunCode.course.dto.CourseDetailResponse;
import com.RunCode.course.dto.CourseSimpleResponse;
import com.RunCode.course.repository.CourseRepository;
import com.RunCode.user.domain.User;
import com.RunCode.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;

    //코스 상세 조회
    public CourseDetailResponse getCourseDetail(Long courseId, Long userId) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 코스를 찾을 수 없습니다."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("해당 유저를 찾을 수 없습니다."));

        boolean isBookmarked = bookmarkRepository.existsByUserAndCourse(user, course);

        return CourseDetailResponse.of(course, isBookmarked);
    }

    // 코스 간단 조회
    public CourseSimpleResponse getCourseSummary(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("해당 코스를 찾을 수 없습니다."));

        return CourseSimpleResponse.of(course);
    }
}
