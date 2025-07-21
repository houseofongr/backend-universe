package com.hoo.universe.api.in.dto;

import java.util.UUID;

public record OverwriteUniverseFileResult(
) {
    public record Thumbmusic(
            UUID deletedThumbmusicID, UUID newThumbmusicID
    ) {
    }
    public record Thumbnail(
            UUID deletedThumbnailID, UUID newThumbnailID
    ) {
    }
    public record Background(
            UUID deletedBackgroundID, UUID newBackgroundID
    ) {
    }
}
