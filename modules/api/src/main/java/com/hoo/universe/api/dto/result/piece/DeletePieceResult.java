package com.hoo.universe.api.dto.result.piece;

import java.util.List;
import java.util.UUID;

public record DeletePieceResult(
        UUID deletedPieceID,
        List<UUID> deletedSoundIDs,
        List<UUID> deletedFileIDs
) {
}
