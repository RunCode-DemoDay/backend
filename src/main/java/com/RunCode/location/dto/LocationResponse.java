package com.RunCode.location.dto;

import com.RunCode.location.domain.LOCATIONTYPE;
import com.RunCode.location.domain.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class LocationResponse {
    double latitude;
    double longitude;
    LOCATIONTYPE locationType;

    public static LocationResponse of (Location lc){
        return LocationResponse.builder()
                .latitude(lc.getLatitude())
                .longitude(lc.getLongitude())
                .locationType(lc.getLocationType())
                .build();
    }
}
