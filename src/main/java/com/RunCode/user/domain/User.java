package com.RunCode.user.domain;

import com.RunCode.archiving.domain.Archiving;
import com.RunCode.bookmark.domain.Bookmark;
import com.RunCode.common.domain.BaseEntity;
import com.RunCode.course.domain.Course;
import com.RunCode.review.domain.Review;
import com.RunCode.type.domain.Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "kakao_id", unique = true, nullable = false)
    private String kakaoId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "nickname", nullable = false, unique = true) //중복x
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = true)
    private Type type;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Archiving> archivings = new ArrayList<>();

    //생성자
    private User(String name, String kakaoId, String profileImage, String nickname, Type type) {
        this.name = name;
        this.kakaoId = kakaoId;
        this.profileImage = profileImage;
        this.nickname = nickname;
        this.type = type;
    }


    //----편의메서드----
    public static User create(String name, String kakaoId, String profileImage, Type type) {
        return new User(name, kakaoId, profileImage, name, type);
    }


    public void updateType(Type newType) {
        this.type = newType;
    }
}
