package com.hoo.universe.api.in.dto;

import com.hoo.universe.domain.vo.Category;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public record UniverseListInfo(
        UUID id,
        URI thumbmusicFileUrl,
        URI thumbnailFileUrl,
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