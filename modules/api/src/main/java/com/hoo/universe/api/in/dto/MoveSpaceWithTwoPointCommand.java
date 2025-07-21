package com.hoo.universe.api.in.dto;

public record MoveSpaceWithTwoPointCommand(
        Double startX,
        Double startY,
        Double endX,
        Double endY
) {
}
