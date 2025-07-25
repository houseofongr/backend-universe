package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.Universe.UniverseID;
import com.hoo.universe.domain.vo.Outline;

public record PieceMoveEvent(
        UniverseID universeID,
        PieceID pieceID,
        OverlapEvent overlapEvent,
        Outline outline
) {
}
