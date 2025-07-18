package com.hoo.aar.application.port.in.universe;

import com.hoo.common.application.port.in.Pagination;

import java.util.List;

public record SearchPublicUniverseResult(
        List<UniverseListInfo> universes,
        Pagination pagination
) {
    public record UniverseListInfo(
            Long id,
            Long thumbnailId,
            Long thumbMusicId,
            Long ownerId,
            Long createdTime,
            Long view,
            Integer likeCnt,
            Boolean isLiked,
            String title,
            String description,
            String owner,
            List<String> hashtags
    ) {

    }

}
