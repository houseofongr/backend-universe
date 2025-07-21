package com.hoo.universe.api.in.dto;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public record CreateUniverseResult(
        UUID universeID,
        URI thumbmusicFileUrl,
        URI thumbnailFileUrl,
        URI backgroundFileUrl,
        UUID ownerID,
        Long createdTime,
        UUID categoryID,
        String title,
        String description,
        String owner,
        String accessLevel,
        List<String> hashtags
) {
}
