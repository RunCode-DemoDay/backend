package com.RunCode.bookmark.dto;

import com.RunCode.bookmark.domain.Bookmark;
import com.RunCode.course.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkListResponse {

    private Long bookmarkId;
    private Long courseId;
    private String title;
    private String content;
    private String thumbnail;
    private Double starAverage;
    private Integer reviewCount;
    private Double distance;

    public static BookmarkListResponse of(Bookmark bookmark) {

        Course course = bookmark.getCourse();

        return BookmarkListResponse.builder()
                .bookmarkId(bookmark.getId())
                .courseId(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .thumbnail(course.getThumbnail())
                .starAverage(course.getStarAverage())
                .reviewCount(course.getReviewCount())
                .distance(course.getDistance())
                .build();
    }
}
