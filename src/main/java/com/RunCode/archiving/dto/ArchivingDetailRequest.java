package com.RunCode.archiving.dto;

import com.RunCode.archiving.domain.Lap;

import java.time.LocalDate;
import java.util.List;


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
    List<Lap> laps;

}
