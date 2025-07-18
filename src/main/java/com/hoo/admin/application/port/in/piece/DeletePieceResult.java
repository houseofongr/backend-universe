package com.hoo.admin.application.port.in.piece;

import java.util.List;

public record DeletePieceResult(
        String message,
        Long deletedPieceId,
        List<Long> deletedSoundIds,
        List<Long> deletedImageFileIds,
        List<Long> deletedAudioFileIds
) {
}
