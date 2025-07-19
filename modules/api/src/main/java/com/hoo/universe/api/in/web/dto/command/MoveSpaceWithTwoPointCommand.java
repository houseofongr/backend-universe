package com.hoo.universe.api.in.web.dto.command;

public record MoveSpaceWithTwoPointCommand(
        Double startX,
        Double startY,
        Double endX,
        Double endY
) {
}
