package com.RunCode.type.domain;

import com.RunCode.tag.domain.TypeTag;
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
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @OneToMany(mappedBy = "type")
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "type")
    private List<TypeTag> typeTags = new ArrayList<>();
}