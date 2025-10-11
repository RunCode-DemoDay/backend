package com.RunCode.tag.domain;

import com.RunCode.course.domain.Course;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "course_tags")
public class CourseTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;


    //생성자
    private CourseTag(Course course, Tag tag) {
        this.course = course;
        this.tag = tag;
    }

    //----편의메서드----
    public static CourseTag create(Course course, Tag tag) {
        CourseTag courseTag = new CourseTag(course, tag);

        course.getCourseTags().add(courseTag);
        tag.getCourseTags().add(courseTag);

        return courseTag;
    }
}