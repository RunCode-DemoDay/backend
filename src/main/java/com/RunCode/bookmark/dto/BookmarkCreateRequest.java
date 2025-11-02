package com.RunCode.bookmark.dto;

import com.RunCode.bookmark.domain.Bookmark;
import com.RunCode.course.domain.Course;
import com.RunCode.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmarkCreateRequest {

    @NotNull
    private Long courseId;

    public Bookmark toEntity(User user, Course course) {
        return Bookmark.create(user, course);
    }
}