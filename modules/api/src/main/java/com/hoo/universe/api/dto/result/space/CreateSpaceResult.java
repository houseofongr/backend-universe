package com.hoo.universe.api.dto.result.space;

import java.util.UUID;

public record CreateSpaceResult(
        UUID spaceID,
        UUID backgroundID,
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
