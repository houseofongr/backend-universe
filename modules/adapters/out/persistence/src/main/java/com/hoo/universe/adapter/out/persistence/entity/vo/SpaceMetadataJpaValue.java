package com.hoo.universe.adapter.out.persistence.entity.vo;

import com.hoo.universe.domain.event.SpaceFileOverwriteEvent;
import com.hoo.universe.domain.event.SpaceMetadataUpdateEvent;
import com.hoo.universe.domain.vo.SpaceMetadata;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.getOrDefault;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SpaceMetadataJpaValue {

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "BACKGROUND_FILE_ID")
    private UUID backgroundFileID;

    @Column(nullable = false)
    private Boolean hidden;

    public static SpaceMetadataJpaValue from(SpaceMetadata spaceMetadata) {
        return new SpaceMetadataJpaValue(
                spaceMetadata.getBackgroundID(),
                spaceMetadata.isHidden()
        );
    }

    public void applyEvent(SpaceMetadataUpdateEvent event) {
        this.hidden = getOrDefault(event.hidden(), hidden);
    }

    public void applyEvent(SpaceFileOverwriteEvent event) {
        this.backgroundFileID = event.newInnerImageID();
    }

}
