package com.RunCode.bookmark.domain;

import com.RunCode.common.domain.BaseEntity;
import com.RunCode.course.domain.Course;
import com.RunCode.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "bookmarks",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "bookmark_uk",
                        columnNames = {"user_id", "course_id"}
                )
        })
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    //생성자
    private Bookmark(User user, Course course) {
        this.user = user;
        this.course = course;
    }

    //----편의메서드----
    public static Bookmark create(User user, Course course) {
        Bookmark bookmark = new Bookmark(user, course);

        user.getBookmarks().add(bookmark);
        course.getBookmarks().add(bookmark);

        return bookmark;
    }
}