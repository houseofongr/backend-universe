package com.hoo.universe.domain.event.piece;

import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.event.OverlapEvent;

public record PieceCreateEvent(
        boolean maxChildExceeded,
        OverlapEvent overlapEvent,
        Piece newPiece
) {
}
