package com.hoo.common.adapter.out.persistence.entity;

import com.hoo.admin.domain.item.soundsource.SoundSource;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SOUND_SOURCE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SoundSourceJpaEntity extends DateColumnBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String description;

    @Column(nullable = false, length = 255)
    private Long audioFileId;

    @Column(nullable = false)
    private Boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private ItemJpaEntity item;

    public static SoundSourceJpaEntity create(SoundSource soundSource) {
        return new SoundSourceJpaEntity(
                null,
                soundSource.getSoundSourceDetail().getName(),
                soundSource.getSoundSourceDetail().getDescription(),
                soundSource.getFile().getFileId().getId(),
                soundSource.getActive().isActive(),
                null
        );
    }

    public void setRelationship(ItemJpaEntity itemJpaEntity) {
        this.item = itemJpaEntity;
        if (!itemJpaEntity.getSoundSources().contains(this))
            itemJpaEntity.getSoundSources().add(this);
    }

    public void update(SoundSource soundSource) {
        this.name = soundSource.getSoundSourceDetail().getName();
        this.description = soundSource.getSoundSourceDetail().getDescription();
        this.isActive = soundSource.getActive().isActive();
    }
}
