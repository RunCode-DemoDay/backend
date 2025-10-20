package com.RunCode.course.domain;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.bookmark.domain.Bookmark;
import com.RunCode.common.domain.BaseEntity;
import com.RunCode.location.domain.Location;
import com.RunCode.review.domain.Review;
import com.RunCode.tag.domain.CourseTag;
import com.RunCode.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "courses")
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "distance_description")
    private String distanceDescription;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_detail_image",
            joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "image_url")
    private List<String> detailImages = new ArrayList<>();

    @Column(name = "star_average")
    private Double starAverage;

    @Column(name = "review_count")
    private Integer reviewCount;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Location> locations = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<CourseTag> courseTags = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Archiving> archivings = new ArrayList<>();

    //생성자
    private Course(User user, String title, String content, String address, Double distance, String distanceDescription, String thumbnail, List<String> detailImages) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.address = address;
        this.distance = distance;
        this.distanceDescription = distanceDescription;
        this.thumbnail = thumbnail;
        this.detailImages = detailImages;
        this.starAverage = 0.0;
        this.reviewCount = 0;
    }

    //----편의 메서드----
    public static Course create(User user, String title, String content, String address, Double distance, String distanceDescription ,String thumbnail, List<String> detailImages) {
        return new Course(user, title, content, address, distance, distanceDescription, thumbnail, detailImages);
    }

    public void addReview(double newStar) {
        double totalStar = this.starAverage * this.reviewCount + newStar;
        this.reviewCount++;
        this.starAverage = totalStar / this.reviewCount;
    }

    public void deleteReview(double deletedStar) {
        double totalStar = this.starAverage * this.reviewCount - deletedStar;
        this.reviewCount--;
        this.starAverage = (this.reviewCount == 0) ? 0.0 : totalStar / this.reviewCount;
    }
}