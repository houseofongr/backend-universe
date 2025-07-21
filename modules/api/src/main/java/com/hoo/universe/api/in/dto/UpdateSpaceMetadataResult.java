package com.hoo.universe.api.in.dto;

public record UpdateSpaceMetadataResult(
        String title,
        String description,
        Boolean hidden,
        Long updatedTime
) {
}
