package com.hoo.universe.api.dto.result.piece;

public record UpdatePieceMetadataResult(
        String title,
        String description,
        Boolean hidden,
        Long updatedTime
) {
}
