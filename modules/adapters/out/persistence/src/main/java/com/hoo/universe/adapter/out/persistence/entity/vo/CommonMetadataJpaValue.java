package com.hoo.universe.adapter.out.persistence.entity.vo;

import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import com.hoo.universe.domain.event.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.event.SoundMetadataUpdateEvent;
import com.hoo.universe.domain.event.SpaceMetadataUpdateEvent;
import com.hoo.universe.domain.vo.CommonMetadata;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

import static com.hoo.common.util.OptionalUtil.getOrDefault;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class CommonMetadataJpaValue {

    @Column(nullable = false, length = 100)
    private String title;

    @Column(length = 5000)
    private String description;

    @Column
    private ZonedDateTime createdTime;

    @Column
    private ZonedDateTime updatedTime;


    public static CommonMetadataJpaValue from(CommonMetadata commonMetadata) {
        return new CommonMetadataJpaValue(
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getCreatedTime(),
                commonMetadata.getUpdatedTime()
        );
    }

    public void applyEvent(String title, String description, ZonedDateTime updatedTime) {
        this.title = getOrDefault(title, this.title);
        this.description = getOrDefault(description, this.description);
        this.updatedTime = updatedTime;
    }

    public void applyEvent(UniverseMetadataUpdateEvent event) {
        applyEvent(event.title(), event.description(), event.updatedTime());
    }

    public void applyEvent(SpaceMetadataUpdateEvent event) {
        applyEvent(event.title(), event.description(), event.updatedTime());
    }

    public void applyEvent(PieceMetadataUpdateEvent event) {
        applyEvent(event.title(), event.description(), event.updatedTime());
    }

    public void applyEvent(SoundMetadataUpdateEvent event) {
        applyEvent(event.title(), event.description(), event.updatedTime());
    }

}
