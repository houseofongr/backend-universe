package com.hoo.universe.api.dto.command.piece;

public record MovePieceWithTwoPointCommand(
        Double startX,
        Double startY,
        Double endX,
        Double endY
) {
}
