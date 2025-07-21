package com.hoo.universe.api.in.dto;

public record MovePieceWithTwoPointCommand(
        Double startX,
        Double startY,
        Double endX,
        Double endY
) {
}
