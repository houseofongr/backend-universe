package com.hoo.universe.api.out.dto;

import com.hoo.universe.domain.vo.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public Map<UUID, UUID> extractFileOwnerMap() {
        Map<UUID, UUID> fileOwnerMap = new HashMap<>();
        fileOwnerMap.put(thumbmusicFileID, ownerID);
        fileOwnerMap.put(thumbnailFileID, ownerID);
        return fileOwnerMap;
    }
}
