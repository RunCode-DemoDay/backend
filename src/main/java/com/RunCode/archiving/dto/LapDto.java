package com.RunCode.archiving.dto;

import com.RunCode.archiving.domain.Lap;
import lombok.*;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class LapDto {
    Long lap_id;
    int lap_number;
    String average_pace;
    String pace_variation;
    int altitude;

    public static LapDto of(Lap lap){
        return LapDto.builder()
                .lap_id(lap.getId())
                .lap_number(lap.getLapNumber())
                .average_pace(lap.getAveragePace())
                .pace_variation(lap.getPaceVariation())
                .altitude(lap.getAltitude())
                .build();
    }
}
