package com.RunCode.review.domain;

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
@Table(name = "reviews",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "review_uk",
                        columnNames = {"user_id", "course_id"}
                )
        })
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "star", nullable = false)
    private Double star;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    //생성자
    private Review(User user, Course course, String title, Double star, String content) {
        this.user = user;
        this.course = course;
        this.title = title;
        this.star = star;
        this.content = content;
    }

    //----편의메서드----
    public static Review create(User user, Course course, String title, Double star, String content) {
        Review review = new Review(user, course, title, star, content);
        course.addReview(star);
        user.getReviews().add(review);
        return review;
    }
}
