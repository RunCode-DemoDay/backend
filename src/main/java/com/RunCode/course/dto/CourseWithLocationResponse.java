package com.RunCode.course.dto;


import com.RunCode.course.domain.Course;
import com.RunCode.location.domain.Location;
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
    Location locations;

    public static CourseWithLocationResponse of (Course course){
        return CourseWithLocationResponse.builder()
                .course_id(course.getId())
                .title(course.getTitle())
                //location start인 경우
                .build();
    }
}
