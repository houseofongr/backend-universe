package com.hoo.universe.api.out.dto;

import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import com.hoo.universe.domain.vo.Category;
import com.hoo.universe.domain.vo.Point;

import java.util.*;

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
    public static OpenUniverseQueryResult from(Universe universe) {
        return new OpenUniverseQueryResult(
                universe.getId().uuid(),
                universe.getUniverseMetadata().getThumbmusicID(),
                universe.getUniverseMetadata().getThumbnailID(),
                universe.getUniverseMetadata().getBackgroundID(),
                universe.getOwner().getId(),
                universe.getCommonMetadata().getCreatedTime().toEpochSecond(),
                universe.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                universe.getUniverseMetadata().getViewCount(),
                universe.getUniverseMetadata().getLikeCount(),
                universe.getCommonMetadata().getTitle(),
                universe.getCommonMetadata().getDescription(),
                universe.getOwner().getNickname(),
                universe.getUniverseMetadata().getAccessLevel().name(),
                universe.getCategory(),
                universe.getUniverseMetadata().getTags(),
                universe.getSpaces().stream().map(s -> SpaceInfo.from(universe, s)).toList(),
                universe.getPieces().stream().map(p -> PieceInfo.from(universe, p)).toList()
        );
    }

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

        public static SpaceInfo from(Universe universe, Space space) {
            return new SpaceInfo(
                    space.getId().uuid(),
                    space.isFirstNode() ? null : space.getParentSpaceID().uuid(),
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
            UUID imageFileID,
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
                    piece.isFirstNode() ? null : piece.getParentSpaceID().uuid(),
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

    public Map<UUID, UUID> extractFileOwnerMap() {

        Map<UUID, UUID> fileOwnerMap = new HashMap<>();

        fileOwnerMap.put(thumbmusicFileID(), ownerID);
        fileOwnerMap.put(thumbnailFileID(), ownerID);
        fileOwnerMap.put(backgroundFileID(), ownerID);

        for (var s : spaces()) collectSpaceFileIds(s, fileOwnerMap);
        for (var p : pieces()) collectPieceFileIds(p, fileOwnerMap);

        return fileOwnerMap;
    }

    private void collectSpaceFileIds(SpaceInfo space, Map<UUID, UUID> fileOwnerMap) {

        fileOwnerMap.put(space.backgroundFileID(), ownerID);

        for (var s : space.spaces()) collectSpaceFileIds(s, fileOwnerMap);
        for (var p : space.pieces()) collectPieceFileIds(p, fileOwnerMap);
    }

    private void collectPieceFileIds(PieceInfo piece, Map<UUID, UUID> fileOwnerMap) {

        fileOwnerMap.put(piece.imageFileID(), ownerID);
    }
}
