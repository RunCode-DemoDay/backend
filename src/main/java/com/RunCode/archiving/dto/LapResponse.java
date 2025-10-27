package com.RunCode.archiving.dto;

import com.RunCode.archiving.domain.Lap;
import lombok.*;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class LapResponse {
    Long lap_id;
    Integer lap_number;
    String average_pace;
    String pace_variation;
    Integer altitude;

    public static LapResponse of(Lap lap){
        return LapResponse.builder()
                .lap_id(lap.getId())
                .lap_number(lap.getLapNumber())
                .average_pace(lap.getAveragePace())
                .pace_variation(lap.getPaceVariation())
                .altitude(lap.getAltitude())
                .build();
    }
}
