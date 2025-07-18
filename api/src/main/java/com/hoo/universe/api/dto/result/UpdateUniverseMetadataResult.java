package com.hoo.universe.api.dto.result;

import com.hoo.universe.domain.vo.Category;

import java.util.List;
import java.util.UUID;

public record UpdateUniverseMetadataResult(
        UUID authorID,
        Long updatedTime,
        String title,
        String description,
        String author,
        String accessStatus,
        Category category,
        List<String> hashtags
) {
}
