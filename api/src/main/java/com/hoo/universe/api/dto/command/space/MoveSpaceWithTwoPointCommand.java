package com.hoo.universe.api.dto.command.space;

public record MoveSpaceWithTwoPointCommand(
        Double startX,
        Double startY,
        Double endX,
        Double endY
) {
}
