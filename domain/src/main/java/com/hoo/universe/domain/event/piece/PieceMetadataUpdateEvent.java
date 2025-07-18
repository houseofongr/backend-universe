package com.hoo.universe.domain.event.piece;

import com.hoo.universe.domain.Piece.PieceID;
import com.hoo.universe.domain.vo.CommonMetadata;
import com.hoo.universe.domain.vo.PieceMetadata;

import java.time.ZonedDateTime;

public record PieceMetadataUpdateEvent(
        PieceID pieceID,
        String title,
        String description,
        ZonedDateTime updatedTime,
        Boolean hidden
) {
    public static PieceMetadataUpdateEvent from(PieceID pieceID, CommonMetadata commonMetadata, PieceMetadata pieceMetadata) {
        return new PieceMetadataUpdateEvent(
                pieceID,
                commonMetadata.getTitle(),
                commonMetadata.getDescription(),
                commonMetadata.getUpdatedTime(),
                pieceMetadata.isHidden()
        );
    }
}
