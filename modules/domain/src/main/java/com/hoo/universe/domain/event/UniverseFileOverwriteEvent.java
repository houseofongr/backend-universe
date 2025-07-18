package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.vo.UniverseMetadata;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public record UniverseFileOverwriteEvent(
        UniverseID universeID,
        UUID oldThumbmusicID,
        UUID oldThumbnailID,
        UUID oldBackgroundID,
        UUID newThumbmusicID,
        UUID newThumbnailID,
        UUID newBackgroundID
) {

    public static UniverseFileOverwriteEvent from(UniverseID universeID, UUID oldThumbmusicID, UUID oldThumbnailID, UUID oldInnerImageID, UniverseMetadata universeMetadata) {
        return new UniverseFileOverwriteEvent(
                universeID,
                equalsNull(oldThumbmusicID, universeMetadata.getThumbmusicID()),
                equalsNull(oldThumbnailID, universeMetadata.getThumbnailID()),
                equalsNull(oldInnerImageID, universeMetadata.getBackgroundID()),
                equalsNull(universeMetadata.getThumbmusicID(), oldThumbmusicID),
                equalsNull(universeMetadata.getThumbnailID(), oldThumbnailID),
                equalsNull(universeMetadata.getBackgroundID(), oldInnerImageID)
        );
    }

    private static UUID equalsNull(UUID target, UUID compare) {
        return target.equals(compare) ? null : target;
    }

    public List<UUID> getOldFileIDs() {
        return Stream.of(oldThumbmusicID, oldThumbnailID, oldBackgroundID)
                .filter(Objects::nonNull)
                .toList();
    }
}
