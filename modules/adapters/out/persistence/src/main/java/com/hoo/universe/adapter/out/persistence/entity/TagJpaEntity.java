package com.hoo.universe.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HASHTAG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TagJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String tag;

    public static TagJpaEntity createNewEntity(String tag) {
        return new TagJpaEntity(null, tag);
    }
}
