package com.hoo.universe.api.in.web.dto.result;

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
