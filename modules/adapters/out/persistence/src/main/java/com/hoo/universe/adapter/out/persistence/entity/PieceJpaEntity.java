package com.hoo.universe.adapter.out.persistence.entity;

import com.hoo.universe.adapter.out.persistence.entity.vo.CommonMetadataJpaValue;
import com.hoo.universe.adapter.out.persistence.entity.vo.PieceMetadataJpaValue;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.event.PieceMetadataUpdateEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PIECE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJpaEntity {

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
    private PieceMetadataJpaValue pieceMetadata;

    @Embedded
    private CommonMetadataJpaValue commonMetadata;

    @Column(length = 1000)
    private String outlinePoints;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "piece", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SoundJpaEntity> sounds;

    public static PieceJpaEntity createNewEntity(Piece piece, UniverseJpaEntity universeJpaEntity, String outlinePoints) {
        return new PieceJpaEntity(
                null,
                piece.getId().uuid(),
                universeJpaEntity,
                piece.getParentSpaceID() != null? piece.getParentSpaceID().uuid() : null,
                PieceMetadataJpaValue.from(piece.getPieceMetadata()),
                CommonMetadataJpaValue.from(piece.getCommonMetadata()),
                outlinePoints,
                List.of()
        );
    }

    public void applyEvent(PieceMetadataUpdateEvent event) {
        this.pieceMetadata.applyEvent(event);
        this.commonMetadata.applyEvent(event);
    }

    public void move(String outlinePoints) {
        this.outlinePoints = outlinePoints;
    }
}
