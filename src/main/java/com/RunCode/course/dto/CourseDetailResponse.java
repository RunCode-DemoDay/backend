package com.RunCode.course.dto;

import com.RunCode.course.domain.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetailResponse {

    private Long courseId;
    private String title;
    private String content;
    private String address;
    private Double distance;
    private String distanceDescription;
    private String thumbnail;
    private List<String> detailImages;
    private Boolean isBookmarked;

    public static CourseDetailResponse of(Course course, Boolean isBookmarked) {
        return CourseDetailResponse.builder()
                .courseId(course.getId())
                .title(course.getTitle())
                .content(course.getContent())
                .address(course.getAddress())
                .distance(course.getDistance())
                .distanceDescription(course.getDistanceDescription())
                .thumbnail(course.getThumbnail())
                .detailImages(new ArrayList<>(course.getDetailImages()))
                .isBookmarked(isBookmarked)
                .build();
    }
}
