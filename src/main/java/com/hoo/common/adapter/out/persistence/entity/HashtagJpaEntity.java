package com.hoo.common.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HASHTAG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HashtagJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String tag;

    public static HashtagJpaEntity create(String tag) {
        return new HashtagJpaEntity(null, tag);
    }
}
