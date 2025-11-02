package com.RunCode.course.repository;

import com.RunCode.course.domain.Course;
import com.RunCode.tag.domain.CourseTag;
import com.RunCode.tag.domain.Tag;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {
    // tag에 따라 코스를 필터링
    public static Specification<Course> hasTag(String tagName) {
        if (tagName == null || tagName.trim().isEmpty()) {
            return null; // 태그가 없으면 조건 무시
        }

        return (root, query, criteriaBuilder) -> {
            // 1. Course -> CourseTag 조인
            Join<Course, CourseTag> courseTagJoin = root.join("courseTags", JoinType.INNER);

            // 2. CourseTag -> Tag 조인
            // CourseTag 엔티티에서 'tag'라는 필드명으로 Tag 엔티티에 접근합니다.
            Join<CourseTag, Tag> tagJoin = courseTagJoin.join("tag", JoinType.INNER);

            // 3. Tag 엔티티의 name 필드와 요청 tagName을 비교
            return criteriaBuilder.equal(tagJoin.get("name"), tagName);
        };
    }

    // 검색어를 코스명이나 코스에 대한 설명에서 찾을 수 있도록
    public static Specification<Course> containsQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return null;
        }
        String pattern = "%" + query.trim() + "%"; //SQL LIKE 패턴!

        return (root, queryBuilder, criteriaBuilder) -> {
            // title LIKE %query%
            Predicate titlePredicate = criteriaBuilder.like(root.get("title"), pattern);
            // content LIKE %query%
            Predicate contentPredicate = criteriaBuilder.like(root.get("content"), pattern);

            // 두 조건을 OR 연산자로 묶어서 반환 (제목에서 찾거나 내용에서 찾음)
            return criteriaBuilder.or(titlePredicate, contentPredicate);
        };
    }
}
