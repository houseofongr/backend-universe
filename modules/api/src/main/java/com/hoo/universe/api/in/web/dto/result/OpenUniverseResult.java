package com.hoo.universe.api.in.web.dto.result;

import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.Point;

import java.net.URI;
import java.util.List;
import java.util.UUID;

public record OpenUniverseResult(
        UUID id,
        URI thumbmusicFileUrl,
        URI thumbnailFileUrl,
        URI backgroundFileUrl,
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
            URI backgroundFileUrl,
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
            URI imageFileUrl,
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
