package com.RunCode.type.dto;

import com.RunCode.tag.dto.TagResponse;
import com.RunCode.type.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeDataResponse {
    private Long type_id;
    private String name;
    private String description;
    private String thumbnail;
    private List<TagResponse> tags;

    public static TypeDataResponse of(Type typeInfo) {

        List<TagResponse> tagResponses = typeInfo.getTypeTags().stream()
                .map(typeTag -> TagResponse.of(typeTag.getTag()))
                .limit(4) // 태그 4개로 제한
                .collect(Collectors.toList());

        return TypeDataResponse.builder()
                .type_id(typeInfo.getId())
                .name(typeInfo.getName())
                .description(typeInfo.getDescription())
                .thumbnail(typeInfo.getThumbnail())
                .tags(tagResponses)
                .build();
    }
}
