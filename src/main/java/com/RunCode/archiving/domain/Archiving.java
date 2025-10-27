package com.RunCode.archiving.domain;

import com.RunCode.common.domain.BaseEntity;
import com.RunCode.course.domain.Course;
import com.RunCode.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA용 기본 생성자
@AllArgsConstructor(access = AccessLevel.PRIVATE)  // Builder 내부에서만 사용
@Builder
@Table(name = "archivings")
public class Archiving extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archiving_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "detail_image")
    private String detailImage;

    @Column(name = "distance", nullable = false)
    private Double distance;

    @Column(name = "calorie", nullable = false)
    private Integer calorie;

    @Column(name = "average_pace", nullable = false)
    private String averagePace;

    @Column(name = "time", nullable = false)
    private String time;

    @Column(name = "altitude", nullable = false)
    private Integer altitude;

    @Column(name = "cadence", nullable = false)
    private Integer cadence;




    @OneToMany(mappedBy = "archiving", cascade = CascadeType.ALL)
    private List<Lap> laps = new ArrayList<>();


    // 생성자
    private Archiving(User user, Course course, LocalDate date, String title, String content, String thumbnail, String detailImage,
                      Double distance, Integer calorie, String averagePace, String time, Integer altitude, Integer cadence) {
        this.user = user;
        this.course = course;
        this.date = date;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.detailImage = detailImage;
        this.distance = distance;
        this.calorie = calorie;
        this.averagePace = averagePace;
        this.time = time;
        this.altitude = altitude;
        this.cadence = cadence;
    }

    //----편의메서드----
    public static Archiving create(User user, Course course, LocalDate date, String title, String content, String thumbnail, String detailImage,
                                   Double distance, Integer calorie, String averagePace, String time, Integer altitude, Integer cadence) {

        Archiving archiving = new Archiving(user, course, date, title, content, thumbnail, detailImage, distance,
                calorie, averagePace, time, altitude, cadence);

        user.getArchivings().add(archiving);
        course.getArchivings().add(archiving);
        return archiving;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}