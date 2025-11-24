package com.RunCode.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // access 설정 불필요 (기본 public)
@AllArgsConstructor
@Builder
public class ReviewStatusResponse {
    Long course_id;
    Long user_id;
    boolean reviewStatus;


}
