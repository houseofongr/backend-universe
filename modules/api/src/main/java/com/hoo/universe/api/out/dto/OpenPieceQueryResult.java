package com.hoo.universe.api.out.dto;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.domain.Piece;

import java.util.HashMap;
import java.util.List;
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

    public Map<UUID, UUID> extractFileIds() {

        Map<UUID, UUID> fileOwnerMap = new HashMap<>();

        for (SoundInfo soundInfo : sounds.content()) {
            fileOwnerMap.put(soundInfo.audioFileID, ownerID);
        }

        return fileOwnerMap;
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
