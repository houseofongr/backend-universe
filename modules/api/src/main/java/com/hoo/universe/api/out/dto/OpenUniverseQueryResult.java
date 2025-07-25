package com.hoo.universe.api.out.dto;

import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.Point;

import java.util.List;
import java.util.UUID;

public record OpenUniverseQueryResult(
        UUID id,
        UUID thumbmusicFileID,
        UUID thumbnailFileID,
        UUID backgroundFileID,
        UUID ownerID,
        Long createdTime,
        Long updatedTime,
        Long view,
        Long like,
        String title,
        String description,
        String owner,
        String accessLevel,
        Category category,
        List<String> hashtags,
        List<SpaceInfo> spaces,
        List<PieceInfo> pieces
) {

    public record SpaceInfo(
            UUID spaceID,
            UUID parentSpaceID,
            UUID backgroundFileID,
            Integer depth,
            String title,
            String description,
            Boolean hidden,
            List<Point> points,
            Long createdTime,
            Long updatedTime,
            List<SpaceInfo> spaces,
            List<PieceInfo> pieces
    ) {
    }

    public record PieceInfo(
            UUID pieceID,
            UUID parentSpaceID,
            UUID imageFileID,
            Integer depth,
            String title,
            String description,
            Boolean hidden,
            List<Point> points,
            Long createdTime,
            Long updatedTime
    ) {
    }

}
