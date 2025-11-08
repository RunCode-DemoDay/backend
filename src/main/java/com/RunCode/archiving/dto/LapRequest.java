package com.RunCode.archiving.dto;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.archiving.domain.Lap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class LapRequest {
    //Long lap_id;
    Integer lap_number;
    String average_pace;
    String pace_variation;
    Integer altitude;

    public Lap toEntity(Archiving archiving){
        return Lap.builder()
                //.id(this.lap_id)
                .archiving(archiving)
                .lapNumber(this.lap_number)
                .averagePace(this.average_pace)
                .paceVariation(this.pace_variation)
                .altitude(this.altitude)
                .build();
    }
}
