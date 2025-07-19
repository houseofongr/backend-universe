package com.hoo.universe.api.in.web.dto.result;

import com.hoo.universe.domain.vo.Category;

import java.util.List;
import java.util.UUID;

public record UpdateUniverseMetadataResult(
        UUID ownerID,
        Long updatedTime,
        String title,
        String description,
        String owner,
        String accessLevel,
        Category category,
        List<String> hashtags
) {
}
