package com.RunCode.archiving.dto;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.archiving.domain.Lap;
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
public class ArchivingDetailRequest {
    Long archiving_id;
    Long user_id;
    Long course_id;
    String title;
    String content;
    LocalDate date;
    String thumbnail;
    String detailImage;
    Double distance;
    int calorie;
    String average_pace;
    String time;
    int altitude;
    int cadence;
    List<LapDto> laps;

    public static ArchivingDetailRequest of(Archiving archiving, List<Lap> laps ){
        return ArchivingDetailRequest.builder()
                .archiving_id(archiving.getId())
                .user_id(archiving.getUser().getId())
                .course_id(archiving.getCourse().getId())
                .title(archiving.getTitle())
                .content(archiving.getContent())
                .thumbnail(archiving.getThumbnail())
                .detailImage(archiving.getDetailImage())
                .calorie(archiving.getCalorie())
                .average_pace(archiving.getAveragePace())
                .time(archiving.getTime())
                .altitude(archiving.getAltitude())
                .cadence(archiving.getCadence())
                .date(archiving.getDate()) // 자동설정 필요한 경우 prePersist()
                .laps(laps.stream().map(LapDto::of).toList())
                .build();
    }
}
