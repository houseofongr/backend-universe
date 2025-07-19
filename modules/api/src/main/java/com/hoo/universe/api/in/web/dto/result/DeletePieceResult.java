package com.hoo.universe.api.in.web.dto.result;

import java.util.List;
import java.util.UUID;

public record DeletePieceResult(
        UUID deletedPieceID,
        List<UUID> deletedSoundIDs,
        Integer deletedFileCount
) {
}
