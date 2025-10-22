package com.RunCode.archiving.dto;


import com.RunCode.archiving.domain.Archiving;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class ArchivingSimpleResponse {
    Long archiving_id;
    Long course_id;
    String title;
    String thumbnail;

    public static ArchivingSimpleResponse of(Archiving archiving){
        return ArchivingSimpleResponse.builder()
                .archiving_id(archiving.getId())
                .course_id(archiving.getCourse().getId())
                .title(archiving.getTitle())
                .thumbnail(archiving.getThumbnail())
                .build();
    }
}
