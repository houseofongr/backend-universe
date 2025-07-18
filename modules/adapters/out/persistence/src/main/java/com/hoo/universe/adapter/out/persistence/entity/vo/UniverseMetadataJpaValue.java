package com.hoo.universe.adapter.out.persistence.entity.vo;

import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.domain.event.UniverseFileOverwriteEvent;
import com.hoo.universe.domain.event.UniverseMetadataUpdateEvent;
import com.hoo.universe.domain.vo.UniverseMetadata;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.getOrDefault;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class UniverseMetadataJpaValue {

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "THUMBMUSIC_FILE_ID")
    private UUID thumbmusicFileID;

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "THUMBNAIL_FILE_ID")
    private UUID thumbnailFileID;

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "BACKGROUND_FILE_ID")
    private UUID backgroundFileID;

    @Column(nullable = false)
    private Long viewCount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccessLevel accessLevel;

    public static UniverseMetadataJpaValue from(UniverseMetadata universeMetadata) {
        return new UniverseMetadataJpaValue(
                universeMetadata.getThumbmusicID(),
                universeMetadata.getThumbnailID(),
                universeMetadata.getBackgroundID(),
                universeMetadata.getViewCount(),
                universeMetadata.getAccessLevel()
        );
    }

    public void applyEvent(UniverseMetadataUpdateEvent event) {
        this.accessLevel = getOrDefault(event.accessLevel(), this.accessLevel);
    }

    public void applyEvent(UniverseFileOverwriteEvent event) {
        this.thumbmusicFileID = getOrDefault(event.newThumbmusicID(), this.thumbmusicFileID);
        this.thumbnailFileID = getOrDefault(event.newThumbnailID(), this.thumbnailFileID);
        this.backgroundFileID = getOrDefault(event.newBackgroundID(), this.backgroundFileID);
    }

}
