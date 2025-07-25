package com.hoo.universe.api.out.dto;

import com.hoo.common.web.dto.PageQueryResult;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record OpenPieceQueryResult(
        UUID pieceID,
        UUID ownerID,
        String title,
        String description,
        Boolean hidden,
        Long createdTime,
        Long updatedTime,
        PageQueryResult<SoundInfo> sounds
) {

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
