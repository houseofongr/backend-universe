package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.universe.space.Space;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SPACE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpaceJpaEntity extends DateColumnBaseEntity {

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

    @Column(nullable = false)
    private Long innerImageFileId;

    @Column(nullable = false)
    private Long universeId;

    @Column
    private Long parentSpaceId;

    @Column(nullable = false)
    private Boolean hidden;

    public static SpaceJpaEntity create(Space space) {
        return new SpaceJpaEntity(
                null,
                space.getBasicInfo().getTitle(),
                space.getBasicInfo().getDescription(),
                space.getPosInfo().getSx(),
                space.getPosInfo().getSy(),
                space.getPosInfo().getEx(),
                space.getPosInfo().getEy(),
                space.getFileInfo().getImageId(),
                space.getBasicInfo().getUniverseId(),
                space.getBasicInfo().getParentSpaceId(),
                space.getBasicInfo().getHidden()
        );
    }

    public void update(Space space) {
        this.innerImageFileId = space.getFileInfo().getImageId();
        this.title = space.getBasicInfo().getTitle();
        this.description = space.getBasicInfo().getDescription();
        this.hidden = space.getBasicInfo().getHidden();
        this.sx = space.getPosInfo().getSx();
        this.sy = space.getPosInfo().getSy();
        this.ex = space.getPosInfo().getEx();
        this.ey = space.getPosInfo().getEy();
    }
}
