package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Sound.SoundID;
import com.hoo.universe.domain.Space.SpaceID;

import java.util.List;
import java.util.UUID;

public record SpaceDeleteEvent(
        List<SpaceID> deleteSpaceIDs,
        List<PieceID> deletePieceIDs,
        List<SoundID> deleteSoundIDs,
        List<UUID> deleteFileIDs
) {
}
