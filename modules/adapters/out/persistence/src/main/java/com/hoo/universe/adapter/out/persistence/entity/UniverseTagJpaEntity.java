package com.hoo.universe.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UNIVERSE_HASHTAG")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UniverseTagJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "UNIVERSE_ID")
    private UniverseJpaEntity universe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HASHTAG_ID")
    private TagJpaEntity hashtag;

    public static UniverseTagJpaEntity createNewEntity(UniverseJpaEntity universeJpaEntity, TagJpaEntity tagJpaEntity) {
        return new UniverseTagJpaEntity(null, universeJpaEntity, tagJpaEntity);
    }
}
