package com.RunCode.archiving.dto;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.course.domain.Course;
import com.RunCode.user.domain.User;
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
    Integer calorie;
    String average_pace;
    String time;
    Integer altitude;
    Integer cadence;
    List<LapRequest> laps;

    public Archiving toEntity(Course course, User user){
        return Archiving.builder()
                .user(user)
                .course(course)
                .title(this.title)
                .content(this.content)
                .thumbnail(this.thumbnail)
                .detailImage(this.detailImage)
                .distance(this.distance)
                .calorie(this.calorie)
                .averagePace(this.average_pace)
                .time(this.time)
                .altitude(this.altitude)
                .cadence(this.cadence)
                .date(this.date != null ? this.date : LocalDate.now())
                .build();
    }
}
