package com.hoo.universe.api.dto.result;

import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.Point;

import java.util.List;
import java.util.UUID;

public record OpenUniverseResult(
        UUID id,
        UUID thumbMusicID,
        UUID thumbnailID,
        UUID backgroundID,
        UUID authorID,
        Long createdTime,
        Long updatedTime,
        Long view,
        Long like,
        String title,
        String description,
        String author,
        String accessStatus,
        Category category,
        List<String> hashtags,
        List<SpaceInfo> spaces,
        List<PieceInfo> pieces
) {

    public static OpenUniverseResult from(Universe universe) {
        return new OpenUniverseResult(
                universe.getId().uuid(),
                universe.getUniverseMetadata().getThumbmusicID(),
                universe.getUniverseMetadata().getThumbnailID(),
                universe.getUniverseMetadata().getBackgroundID(),
                universe.getAuthor().getId(),
                universe.getCommonMetadata().getCreatedTime().toEpochSecond(),
                universe.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                universe.getUniverseMetadata().getViewCount(),
                universe.getUniverseMetadata().getLikeCount(),
                universe.getCommonMetadata().getTitle(),
                universe.getCommonMetadata().getDescription(),
                universe.getAuthor().getNickname(),
                universe.getUniverseMetadata().getAccessStatus().name(),
                universe.getCategory(),
                universe.getUniverseMetadata().getTags(),
                universe.getSpaces().stream().map(s -> SpaceInfo.from(universe, s)).toList(),
                universe.getPieces().stream().map(p -> PieceInfo.from(universe, p)).toList()
        );
    }

    public record SpaceInfo(
            UUID spaceID,
            UUID parentSpaceID,
            UUID backgroundID,
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

        public static SpaceInfo from(Universe universe, Space space) {
            return new SpaceInfo(
                    space.getId().uuid(),
                    space.isFirstNode()? null : space.getParentSpaceID().uuid(),
                    space.getSpaceMetadata().getBackgroundID(),
                    universe.getDepth(space.getId()),
                    space.getCommonMetadata().getTitle(),
                    space.getCommonMetadata().getDescription(),
                    space.getSpaceMetadata().isHidden(),
                    space.getOutline().getPoints(),
                    space.getCommonMetadata().getCreatedTime().toEpochSecond(),
                    space.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                    space.getSpaces().stream().map(s -> SpaceInfo.from(universe, s)).toList(),
                    space.getPieces().stream().map(p -> PieceInfo.from(universe, p)).toList()
            );
        }
    }

    public record PieceInfo(
            UUID pieceID,
            UUID parentSpaceID,
            UUID imageID,
            Integer depth,
            String title,
            String description,
            Boolean hidden,
            List<Point> points,
            Long createdTime,
            Long updatedTime
    ) {

        public static PieceInfo from(Universe universe, Piece piece) {
            return new PieceInfo(
                    piece.getId().uuid(),
                    piece.isFirstNode()? null : piece.getParentSpaceID().uuid(),
                    piece.getPieceMetadata().getImageID(),
                    universe.getDepth(piece.getId()),
                    piece.getCommonMetadata().getTitle(),
                    piece.getCommonMetadata().getDescription(),
                    piece.getPieceMetadata().isHidden(),
                    piece.getOutline().getPoints(),
                    piece.getCommonMetadata().getCreatedTime().toEpochSecond(),
                    piece.getCommonMetadata().getUpdatedTime().toEpochSecond()
            );
        }
    }
}
