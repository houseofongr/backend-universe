package com.hoo.universe.api.in.dto;

public record UpdatePieceMetadataResult(
        String title,
        String description,
        Boolean hidden,
        Long updatedTime
) {
}
