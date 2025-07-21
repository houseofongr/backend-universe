package com.hoo.universe.api.in.dto;

public record UpdateSoundMetadataResult(
        String title,
        String description,
        Boolean hidden,
        Long updatedTime
) {
}
