package com.hoo.universe.api.dto.result.space;

import java.util.List;
import java.util.UUID;

public record DeleteSpaceResult(
        List<UUID> deletedSpaceIDs,
        List<UUID> deletedPieceIDs,
        List<UUID> deletedSoundIDs,
        Integer deletedFileCount
) {
}
