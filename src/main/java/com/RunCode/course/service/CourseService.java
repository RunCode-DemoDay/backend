package com.RunCode.course.service;

import com.RunCode.bookmark.repository.BookmarkRepository;
import com.RunCode.course.domain.Course;
import com.RunCode.course.dto.CourseListResponse;
import com.RunCode.course.repository.CourseRepository;
import com.RunCode.course.repository.CourseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;
    private final BookmarkRepository bookmarkRepository;

    // tag & order에 따라 코스 목록 조회 후 DTO로 변환
    public List<CourseListResponse> getCoursesByTagAndOrder(String tag, String order, Long userId) {

        //  Tag를 통해 WHERE절 만들기
        Specification<Course> spec = CourseSpecification.hasTag(tag);
        // Order를 통해 ORDER BY절 만들기
        Sort sort = convertOrderToSort(order);
        // DB 조회
        List<Course> courses = courseRepository.findAll(spec, sort);

        // DTO 변환 로직을 CourseListResponse.of()에 위임
        return courses.stream()
                .map(course -> {
                    boolean isBookmarked = isCourseBookmarked(course.getId(), userId);
                    return CourseListResponse.of(course, isBookmarked);
                })
                .collect(Collectors.toList());
    }

    // ⭐️ Course 검색 결과 조회 메서드 추가 (Controller에서 호출)
    public List<CourseListResponse> searchCoursesByQueryAndOrder(String query, String order, Long userId) {

        // WHERE: 쿼리 검색 조건 사용
        Specification<Course> spec = CourseSpecification.containsQuery(query);
        // ORDER BY
        Sort sort = convertOrderToSort(order);

        List<Course> courses = courseRepository.findAll(spec, sort);

        return courses.stream()
                .map(course -> {
                    boolean isBookmarked = isCourseBookmarked(course.getId(), userId);
                    return CourseListResponse.of(course, isBookmarked);
                })
                .collect(Collectors.toList());
    }

    private Sort convertOrderToSort(String order) {
        Sort.Direction direction = Sort.Direction.DESC; // 기본 내림차순!!
        String property; // 엔티티 필드명

        switch (order) {
            case "리뷰순":
                property = "reviewCount";
                break;
            case "별점순":
                property = "starAverage";
                break;
            case "짧은코스순":
                property = "distance";
                direction = Sort.Direction.ASC; // 오름차순
                break;
            case "긴코스순":
                property = "distance";
                break;
            case "최신순":
            default:
                property = "createdAt"; // BaseEntity에 있는 필드
                break;
        }
        return Sort.by(direction, property);
    }

    // BookmarkRepository에서 사용자의 북마크 여부 확인
    private boolean isCourseBookmarked(Long courseId, Long userId) {
        return bookmarkRepository.existsByUserIdAndCourseId(userId, courseId);
    }


}
