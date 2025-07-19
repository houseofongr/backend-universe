package com.hoo.universe.api.in.web.dto.result;

import java.net.URI;
import java.util.UUID;

public record CreateSpaceResult(
        UUID spaceID,
        URI backgroundFileUrl,
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
