package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.universe.piece.Piece;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PIECE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PieceJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false)
    private Float sx;

    @Column(nullable = false)
    private Float sy;

    @Column(nullable = false)
    private Float ex;

    @Column(nullable = false)
    private Float ey;

    @Column
    private Long innerImageFileId;

    @Column(nullable = false)
    private Long universeId;

    @Column
    private Long parentSpaceId;

    @Column(nullable = false)
    private Boolean hidden;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "piece")
    private List<SoundJpaEntity> sounds;

    public static PieceJpaEntity create(Piece piece) {
        return new PieceJpaEntity(
                null,
                piece.getBasicInfo().getTitle(),
                piece.getBasicInfo().getDescription(),
                piece.getPosInfo().getSx(),
                piece.getPosInfo().getSy(),
                piece.getPosInfo().getEx(),
                piece.getPosInfo().getEy(),
                piece.getFileInfo().getImageId(),
                piece.getBasicInfo().getUniverseId(),
                piece.getBasicInfo().getParentSpaceId(),
                piece.getBasicInfo().getHidden(),
                new ArrayList<>()
        );
    }

    public void update(Piece piece) {
        this.innerImageFileId = piece.getFileInfo().getImageId();
        this.title = piece.getBasicInfo().getTitle();
        this.description = piece.getBasicInfo().getDescription();
        this.sx = piece.getPosInfo().getSx();
        this.sy = piece.getPosInfo().getSy();
        this.ex = piece.getPosInfo().getEx();
        this.ey = piece.getPosInfo().getEy();
    }
}
