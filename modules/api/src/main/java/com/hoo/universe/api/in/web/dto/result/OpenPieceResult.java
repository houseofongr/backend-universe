package com.hoo.universe.api.in.web.dto.result;

import com.hoo.common.web.dto.PageQueryResult;

import java.net.URI;
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

    public record SoundInfo(
            UUID soundID,
            URI audioFileUrl,
            String title,
            String description,
            Boolean hidden,
            Long createdTime,
            Long updatedTime
    ) {

    }
}
