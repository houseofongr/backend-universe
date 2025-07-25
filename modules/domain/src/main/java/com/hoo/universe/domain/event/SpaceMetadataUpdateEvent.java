package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Space.SpaceID;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.SpaceMetadata;

import java.time.ZonedDateTime;

public record SpaceMetadataUpdateEvent(
        SpaceID spaceID,
        String title,
        String description,
        ZonedDateTime updatedTime,
        Boolean hidden
) {
    public static SpaceMetadataUpdateEvent from(SpaceID spaceID, CommonMetadata commonMetadata, SpaceMetadata spaceMetadata) {
        return new SpaceMetadataUpdateEvent(
                spaceID,
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                spaceMetadata.isHidden()
        );
    }
}
