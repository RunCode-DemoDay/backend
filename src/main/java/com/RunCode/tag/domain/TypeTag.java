package com.RunCode.tag.domain;

import com.RunCode.type.domain.Type;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "type_tags")
public class TypeTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    //생성자
    private TypeTag(Type type, Tag tag) {
        this.type = type;
        this.tag = tag;
    }

    //----편의메서드----
    public static TypeTag create(Type type, Tag tag) {
        TypeTag typeTag = new TypeTag(type, tag);

        type.getTypeTags().add(typeTag);
        tag.getTypeTags().add(typeTag);

        return typeTag;
    }
}
