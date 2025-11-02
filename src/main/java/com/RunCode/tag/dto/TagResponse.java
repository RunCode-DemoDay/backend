package com.RunCode.tag.dto;

import com.RunCode.tag.domain.Tag;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagResponse {
    Long tag_id;
    String name;

    public static TagResponse of(Tag tag) {
        return TagResponse.builder()
                .tag_id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
