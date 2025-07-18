package com.hoo.universe.api.dto.result;

import com.hoo.universe.domain.vo.Category;

import java.util.List;
import java.util.UUID;

public record UniverseListInfo(
        UUID id,
        UUID thumbmusicID,
        UUID thumbnailID,
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