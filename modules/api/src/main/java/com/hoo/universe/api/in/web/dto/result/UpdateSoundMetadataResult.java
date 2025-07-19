package com.hoo.universe.api.in.web.dto.result;

public record UpdateSoundMetadataResult(
        String title,
        String description,
        Boolean hidden,
        Long updatedTime
) {
}
