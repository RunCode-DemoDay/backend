package com.RunCode.course.dto;


import com.RunCode.course.domain.Course;
import com.RunCode.location.domain.LOCATIONTYPE;
import com.RunCode.location.domain.Location;
import com.RunCode.location.dto.LocationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class CourseWithLocationResponse {
    Long course_id;
    String title;
    LocationResponse location;

    public static CourseWithLocationResponse of (Course course){
        LocationResponse lr = course.getLocations().stream()
                .filter(l -> l.getLocationType() == LOCATIONTYPE.START)
                .findFirst()
                .map(LocationResponse::of)
                .orElse(null);
        return CourseWithLocationResponse.builder()
                .course_id(course.getId())
                .title(course.getTitle())
                //location start인 경우
                .location(lr)
                .build();
    }
}
