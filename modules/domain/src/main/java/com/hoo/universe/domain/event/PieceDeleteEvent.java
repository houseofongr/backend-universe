package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound.SoundID;

import java.util.List;
import java.util.UUID;

public record PieceDeleteEvent(
        PieceID deletePieceID,
        List<SoundID> deleteSoundIDs,
        List<UUID> deleteFileIDs
) {
}
