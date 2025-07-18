package com.hoo.universe.api.dto.result.sound;

public record UpdateSoundMetadataResult(
        String title,
        String description,
        Boolean hidden,
        Long updatedTime
) {
}
