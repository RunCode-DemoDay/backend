package com.RunCode.archiving.dto;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.archiving.domain.Lap;
import com.RunCode.course.dto.CourseSimpleReponse;
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
public class ArchivingDetailResponse {

    Long archiving_id;
    Long user_id;
    //Long course_id;
    String title;
    String content;
    LocalDate date;
    String thumbnail;
    String detailImage;
    Double distance;
    Integer calorie;
    String average_pace;
    String time;
    Integer altitude;
    Integer cadence;
    List<LapResponse> laps;
    CourseSimpleReponse course;

    public static ArchivingDetailResponse of(Archiving archiving, List<Lap> lap) {
        return ArchivingDetailResponse.builder()
                .archiving_id(archiving.getId())
                .user_id(archiving.getUser().getId())
                .title(archiving.getTitle())
                .content(archiving.getContent())
                .date(archiving.getDate())
                .thumbnail(archiving.getThumbnail())
                .detailImage(archiving.getDetailImage())
                .distance(archiving.getDistance())
                .calorie(archiving.getCalorie())
                .average_pace(archiving.getAveragePace())
                .time(archiving.getTime())
                .altitude(archiving.getAltitude())
                .cadence(archiving.getCadence())
                .course(CourseSimpleReponse.of(archiving.getCourse()))
                .laps(archiving.getLaps().stream().map(LapResponse::of).toList())
                .build();
    }



}
