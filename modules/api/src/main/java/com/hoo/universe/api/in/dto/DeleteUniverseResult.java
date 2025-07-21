package com.hoo.universe.api.in.dto;

import java.util.List;
import java.util.UUID;

public record DeleteUniverseResult(
        UUID deletedUniverseID,
        List<UUID> deletedSpaceIDs,
        List<UUID> deletedPieceIDs,
        List<UUID> deletedSoundIDs,
        Integer deletedFileCount
) {
}
