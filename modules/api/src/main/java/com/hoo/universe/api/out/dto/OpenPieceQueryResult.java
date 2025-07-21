package com.hoo.universe.api.out.dto;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.domain.Piece;

import java.util.List;
import java.util.UUID;

public record OpenPieceQueryResult(
        UUID pieceID,
        String title,
        String description,
        Boolean hidden,
        Long createdTime,
        Long updatedTime,
        PageQueryResult<SoundInfo> sounds
) {

    public List<UUID> extractFileIds() {

        return sounds.content().stream().map(SoundInfo::audioFileID).toList();
    }

    public static OpenPieceQueryResult from(Piece piece, PageQueryResult<SoundInfo> sounds) {

        return new OpenPieceQueryResult(
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
            UUID audioFileID,
            String title,
            String description,
            Boolean hidden,
            Long createdTime,
            Long updatedTime
    ) {

    }
}
