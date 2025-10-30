package com.RunCode.bookmark.dto;

import com.RunCode.bookmark.domain.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkCreateResponse {

    private Long bookmarkId;
    private Boolean isBookmarked;

    public static BookmarkCreateResponse of(Bookmark bookmark) {
        return BookmarkCreateResponse.builder()
                .bookmarkId(bookmark.getId())
                .isBookmarked(true)
                .build();
    }
}
