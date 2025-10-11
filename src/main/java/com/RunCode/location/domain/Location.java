package com.RunCode.location.domain;

import com.RunCode.course.domain.Course;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "location_type", nullable = false)
    private LOCATIONTYPE locationType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    //생성자
    private Location(Course course, Double latitude, Double longitude, LOCATIONTYPE locationType) {
        this.course = course;
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationType = locationType;
    }

    //----편의메서드----
    public static Location create(Course course, Double latitude, Double longitude, LOCATIONTYPE locationType) {
        Location location = new Location(course, latitude, longitude, locationType);
        course.getLocations().add(location);
        return location;
    }
}