package com.hoo.universe.domain.event;

import com.hoo.universe.domain.Piece;

public record PieceCreateEvent(
        boolean maxChildExceeded,
        OverlapEvent overlapEvent,
        Piece newPiece
) {
}
