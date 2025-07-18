package com.hoo.admin.application.port.in.universe;

import java.util.List;

public record CreateUniverseResult(
        String message,
        Long universeId,
        Long thumbMusicId,
        Long thumbnailId,
        Long innerImageId,
        Long authorId,
        Long createdTime,
        Long categoryId,
        String title,
        String description,
        String author,
        String publicStatus,
        List<String> hashtags
) {
}
