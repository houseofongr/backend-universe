package com.hoo.admin.application.port.in.piece;

public record CreatePieceResult(
        String message,
        Long pieceId,
        String title,
        String description,
        Float startX,
        Float startY,
        Float endX,
        Float endY,
        Boolean hidden
) {

}
