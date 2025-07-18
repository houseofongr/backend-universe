package com.hoo.common.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UNIVERSE_HASHTAG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UniverseHashtagJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UNIVERSE_ID")
    private UniverseJpaEntity universe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HASHTAG_ID")
    private HashtagJpaEntity hashtag;

    public static UniverseHashtagJpaEntity create(UniverseJpaEntity universeJpaEntity, HashtagJpaEntity hashtagJpaEntity) {
        return new UniverseHashtagJpaEntity(null, universeJpaEntity, hashtagJpaEntity);
    }
}
