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
public class BookmarkDeleteResponse {

    private Long bookmarkId;
    private Boolean isBookmarked;

    public static BookmarkDeleteResponse of(Bookmark bookmark) {
        return BookmarkDeleteResponse.builder()
                .bookmarkId(bookmark.getId())
                .isBookmarked(false)
                .build();
    }
}
