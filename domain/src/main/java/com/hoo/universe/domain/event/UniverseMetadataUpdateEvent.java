package com.hoo.universe.domain.event;

import com.hoo.common.enums.AccessLevel;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.Owner;
import com.hoo.universe.domain.vo.UniverseMetadata;

import java.time.ZonedDateTime;
import java.util.List;

public record UniverseMetadataUpdateEvent(
        UniverseID universeID,
        Category category,
        Owner owner,
        String title,
        String description,
        ZonedDateTime updatedTime,
        AccessLevel accessLevel,
        List<String> tags
) {

    public static UniverseMetadataUpdateEvent from(UniverseID universeID, Category category, Owner owner, CommonMetadata commonMetadata, UniverseMetadata universeMetadata) {
        return new UniverseMetadataUpdateEvent(
                universeID,
                category,
                owner,
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                universeMetadata.getAccessLevel(),
                universeMetadata.getTags()
        );
    }
}
