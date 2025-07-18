package com.hoo.universe.adapter.out.persistence.entity;

import com.hoo.universe.adapter.out.persistence.entity.vo.AuthorJpaValue;
import com.hoo.universe.adapter.out.persistence.entity.vo.CommonMetadataJpaValue;
import com.hoo.universe.adapter.out.persistence.entity.vo.UniverseMetadataJpaValue;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "UNIVERSE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UniverseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private CategoryJpaEntity category;

    @Embedded
    private AuthorJpaValue owner;

    @Embedded
    private UniverseMetadataJpaValue universeMetadata;

    @Embedded
    private CommonMetadataJpaValue commonMetadata;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe", cascade = CascadeType.REMOVE)
    private List<UniverseLikeJpaEntity> universeLikes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<UniverseTagJpaEntity> universeHashtags;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<SpaceJpaEntity> spaces;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "universe", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<PieceJpaEntity> pieces;


    public static UniverseJpaEntity createNewEntity(Universe universe, CategoryJpaEntity categoryJpaEntity) {
        return new UniverseJpaEntity(
                null,
                universe.getId().uuid(),
                categoryJpaEntity,
                AuthorJpaValue.from(universe.getOwner()),
                UniverseMetadataJpaValue.from(universe.getUniverseMetadata()),
                CommonMetadataJpaValue.from(universe.getCommonMetadata()),
                List.of(),
                new ArrayList<>(),
                List.of(),
                List.of()
        );
    }

    public void applyEvent(UniverseMetadataUpdateEvent event, CategoryJpaEntity categoryJpaEntity) {
        if (categoryJpaEntity != null) this.category = categoryJpaEntity;
        this.universeMetadata.applyEvent(event);
        this.commonMetadata.applyEvent(event);
    }

    public void applyEvent(UniverseFileOverwriteEvent event) {
        this.universeMetadata.applyEvent(event);
    }
}
