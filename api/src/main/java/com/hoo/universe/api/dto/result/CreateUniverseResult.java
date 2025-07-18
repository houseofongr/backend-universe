package com.hoo.universe.api.dto.result;

import java.util.List;
import java.util.UUID;

public record CreateUniverseResult(
        UUID universeID,
        UUID thumbMusicID,
        UUID thumbnailID,
        UUID backgroundID,
        UUID authorID,
        Long createdTime,
        UUID categoryID,
        String title,
        String description,
        String author,
        String accessStatus,
        List<String> hashtags
) {
}
