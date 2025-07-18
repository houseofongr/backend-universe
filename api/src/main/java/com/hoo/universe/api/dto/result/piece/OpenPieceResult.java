package com.hoo.universe.api.dto.result.piece;

import com.hoo.common.internal.api.dto.PageQueryResult;
import com.hoo.universe.domain.Piece;

import java.util.UUID;

public record OpenPieceResult(
        UUID pieceID,
        String title,
        String description,
        Boolean hidden,
        Long createdTime,
        Long updatedTime,
        PageQueryResult<SoundInfo> sounds
) {

    public static OpenPieceResult from(Piece piece, PageQueryResult<SoundInfo> sounds) {

        return new OpenPieceResult(
                piece.getId().uuid(),
                piece.getCommonMetadata().getTitle(),
                piece.getCommonMetadata().getDescription(),
                piece.getPieceMetadata().isHidden(),
                piece.getCommonMetadata().getCreatedTime().toEpochSecond(),
                piece.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                sounds
        );
    }

    public record SoundInfo(
            UUID soundID,
            UUID audioID,
            String title,
            String description,
            Boolean hidden,
            Long createdTime,
            Long updatedTime
    ) {

    }
}
