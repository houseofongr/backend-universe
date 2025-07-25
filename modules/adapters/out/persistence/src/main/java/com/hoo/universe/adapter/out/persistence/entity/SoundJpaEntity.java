package com.hoo.universe.adapter.out.persistence.entity;

import com.hoo.universe.adapter.out.persistence.entity.vo.CommonMetadataJpaValue;
import com.hoo.universe.adapter.out.persistence.entity.vo.SoundMetadataJpaValue;
import com.hoo.universe.domain.Sound;
import com.hoo.universe.domain.event.SoundFileOverwriteEvent;
import com.hoo.universe.domain.event.SoundMetadataUpdateEvent;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "SOUND")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SoundJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, unique = true)
    private UUID uuid;

    // ParentPiece
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PIECE_ID")
    private PieceJpaEntity piece;

    @Embedded
    private SoundMetadataJpaValue soundMetadata;

    @Embedded
    private CommonMetadataJpaValue commonMetadata;

    public static SoundJpaEntity createNewEntity(Sound sound, PieceJpaEntity pieceJpaEntity) {
        return new SoundJpaEntity(
                null,
                sound.getId().uuid(),
                pieceJpaEntity,
                SoundMetadataJpaValue.from(sound.getSoundMetadata()),
                CommonMetadataJpaValue.from(sound.getCommonMetadata())
        );
    }

    public void applyEvent(SoundMetadataUpdateEvent event) {
        this.soundMetadata.applyEvent(event);
        this.commonMetadata.applyEvent(event);
    }

    public void applyEvent(SoundFileOverwriteEvent event) {
        this.soundMetadata.applyEvent(event);
    }
}
