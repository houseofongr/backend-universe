package com.hoo.admin.application.port.in.universe;

import java.util.List;

public record CreateUniverseResult(
        String message,
        Long universeId,
        Long thumbmusicId,
        Long thumbnailId,
        Long innerImageId,
        Long ownerId,
        Long createdTime,
        Long categoryId,
        String title,
        String description,
        String owner,
        String publicStatus,
        List<String> hashtags
) {
}
