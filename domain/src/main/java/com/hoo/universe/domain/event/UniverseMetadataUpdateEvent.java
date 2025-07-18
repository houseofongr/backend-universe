package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.vo.*;

import java.time.ZonedDateTime;
import java.util.List;

public record UniverseMetadataUpdateEvent(
        UniverseID universeID,
        Category category,
        Author author,
        String title,
        String description,
        ZonedDateTime updatedTime,
        AccessStatus accessStatus,
        List<String> tags
) {

    public static UniverseMetadataUpdateEvent from(UniverseID universeID, Category category, Author author, CommonMetadata commonMetadata, UniverseMetadata universeMetadata) {
        return new UniverseMetadataUpdateEvent(
                universeID,
                category,
                author,
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                universeMetadata.getAccessStatus(),
                universeMetadata.getTags()
        );
    }
}
