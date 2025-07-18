package com.hoo.universe.api.dto.query;

import com.hoo.universe.domain.vo.Category;

import java.util.List;
import java.util.UUID;

public record UniverseListQueryInfo(
        UUID id,
        UUID thumbmusicFileID,
        UUID thumbnailFileID,
        UUID ownerID,
        Long createdTime,
        Long updatedTime,
        Long view,
        Integer like,
        String title,
        String description,
        String owner,
        String accessLevel,
        Category category,
        List<String> hashtags
) {
}
