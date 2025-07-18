package com.hoo.universe.api.dto.result.space;

public record UpdateSpaceMetadataResult(
        String title,
        String description,
        Boolean hidden,
        Long updatedTime
) {
}
