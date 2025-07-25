package com.hoo.universe.api.out;

import com.hoo.universe.domain.event.PieceMetadataUpdateEvent;
import com.hoo.universe.domain.event.PieceMoveEvent;

public interface UpdatePieceStatusPort {


    void updatePieceMetadata(PieceMetadataUpdateEvent event);

    void updatePieceMove(PieceMoveEvent event);

}
