package com.hoo.universe.api.dto.result.piece;

import java.util.UUID;

public record CreatePieceResult(
        UUID pieceID,
        String title,
        String description,
        Double startX,
        Double startY,
        Double endX,
        Double endY,
        Boolean hidden,
        Long createdTime
) {

}
