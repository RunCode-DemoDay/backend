package com.RunCode.archiving.dto;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.course.dto.CourseSimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class ArchivingSummaryResponse {
    Long archiving_id;
    Long course_id;
    String title;
    LocalDate date;
    String thumbnail;
    Double distance;
    String average_pace;
    String time;

    public static ArchivingSummaryResponse of (Archiving archiving){
        return ArchivingSummaryResponse.builder()
                .archiving_id(archiving.getId())
                .course_id(archiving.getCourse().getId())
                .title(archiving.getTitle())
                .thumbnail(archiving.getThumbnail())
                .date(archiving.getDate())
                .distance(archiving.getDistance())
                .average_pace(archiving.getAveragePace())
                .time(archiving.getTime())
                .build();
    }


}
