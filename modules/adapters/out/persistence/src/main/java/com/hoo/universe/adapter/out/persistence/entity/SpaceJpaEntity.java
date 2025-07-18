package com.hoo.universe.adapter.out.persistence.entity;

import com.hoo.universe.adapter.out.persistence.entity.vo.CommonMetadataJpaValue;
import com.hoo.universe.adapter.out.persistence.entity.vo.SpaceMetadataJpaValue;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.event.space.SpaceFileOverwriteEvent;
import com.hoo.universe.domain.event.space.SpaceMetadataUpdateEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "SPACE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID uuid;

    // Universe(Root)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "UNIVERSE_ID")
    private UniverseJpaEntity universe;

    // ParentSpace
    @Column(columnDefinition = "BINARY(16)")
    private UUID parentSpaceId;

    @Embedded
    private SpaceMetadataJpaValue spaceMetadata;

    @Embedded
    private CommonMetadataJpaValue commonMetadata;

    @Column(length = 1000)
    private String outlinePoints;


    public static SpaceJpaEntity createNewEntity(Space space, UniverseJpaEntity universeJpaEntity, String outlinePoints) {
        return new SpaceJpaEntity(
                null,
                space.getId().uuid(),
                universeJpaEntity,
                space.getParentSpaceID() != null? space.getParentSpaceID().uuid() : null,
                SpaceMetadataJpaValue.from(space.getSpaceMetadata()),
                CommonMetadataJpaValue.from(space.getCommonMetadata()),
                outlinePoints
        );
    }

    public void applyEvent(SpaceMetadataUpdateEvent event) {
        this.spaceMetadata.applyEvent(event);
        this.commonMetadata.applyEvent(event);
    }

    public void applyEvent(SpaceFileOverwriteEvent event) {
        this.spaceMetadata.applyEvent(event);
    }

    public void move(String outlinePoints) {
        this.outlinePoints = outlinePoints;
    }
}
