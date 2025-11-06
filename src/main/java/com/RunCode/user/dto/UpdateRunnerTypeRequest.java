package com.RunCode.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRunnerTypeRequest {
    @JsonProperty("type_id")
    private Long typeId;
}