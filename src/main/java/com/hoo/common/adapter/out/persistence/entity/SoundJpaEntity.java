package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.universe.piece.sound.Sound;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SOUND")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SoundJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private Long audioFileId;

    @Column(nullable = false)
    private Boolean hidden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PIECE_ID")
    private PieceJpaEntity piece;

    public static SoundJpaEntity create(Sound sound, PieceJpaEntity pieceJpaEntity) {
        return new SoundJpaEntity(null,
                sound.getBasicInfo().getTitle(),
                sound.getBasicInfo().getDescription(),
                sound.getFileInfo().getAudioId(),
                sound.getBasicInfo().getHidden(),
                pieceJpaEntity
        );
    }

    public void update(Sound sound) {
        this.audioFileId = sound.getFileInfo().getAudioId();
        this.title = sound.getBasicInfo().getTitle();
        this.description = sound.getBasicInfo().getDescription();
        this.hidden = sound.getBasicInfo().getHidden();
    }
}
