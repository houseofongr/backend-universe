package com.hoo.universe.api.in.web.dto.result;

public record UpdatePieceMetadataResult(
        String title,
        String description,
        Boolean hidden,
        Long updatedTime
) {
}
