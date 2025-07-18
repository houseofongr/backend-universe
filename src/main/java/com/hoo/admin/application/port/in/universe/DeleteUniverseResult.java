package com.hoo.admin.application.port.in.universe;

import java.util.List;

public record DeleteUniverseResult(
        String message,
        Long deletedUniverseId,
        List<Long> deletedSpaceIds,
        List<Long> deletedPieceIds,
        List<Long> deletedSoundIds,
        List<Long> deletedImageFileIds,
        List<Long> deletedAudioFileIds
) {
}
