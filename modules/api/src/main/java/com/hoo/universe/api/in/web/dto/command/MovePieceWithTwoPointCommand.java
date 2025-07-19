package com.hoo.universe.api.in.web.dto.command;

public record MovePieceWithTwoPointCommand(
        Double startX,
        Double startY,
        Double endX,
        Double endY
) {
}
