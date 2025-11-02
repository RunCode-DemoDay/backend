package com.RunCode.archiving.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class ArchivingUpdateRequest {

    String title;
    String content;


}
