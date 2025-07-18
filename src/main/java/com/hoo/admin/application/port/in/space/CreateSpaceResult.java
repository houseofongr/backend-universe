package com.hoo.admin.application.port.in.space;

public record CreateSpaceResult(
        String message,
        Long spaceId,
        Long innerImageId,
        String title,
        String description,
        Float startX,
        Float startY,
        Float endX,
        Float endY,
        Boolean hidden
) {
}
