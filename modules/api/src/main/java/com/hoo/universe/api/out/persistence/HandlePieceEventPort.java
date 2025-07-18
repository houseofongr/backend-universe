package com.hoo.universe.api.out.persistence;

import com.hoo.universe.domain.event.piece.PieceCreateEvent;
import com.hoo.universe.domain.event.piece.PieceDeleteEvent;
import com.hoo.universe.domain.event.piece.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.event.piece.PieceMoveEvent;

public interface HandlePieceEventPort {
    void handlePieceCreateEvent(PieceCreateEvent event);

    void handlePieceMetadataUpdateEvent(PieceMetadataUpdateEvent event);

    void handlePieceMoveEvent(PieceMoveEvent event);

    void handlePieceDeleteEvent(PieceDeleteEvent event);
}
