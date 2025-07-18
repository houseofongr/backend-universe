package com.hoo.aar.application.port.in.universe;

import com.hoo.aar.application.service.AarErrorCode;
import com.hoo.aar.application.service.AarException;
import com.hoo.admin.application.port.in.category.CategoryInfo;
import com.hoo.admin.application.port.in.universe.TraversalUniverseResult;
import com.hoo.admin.domain.universe.TreeInfo;
import com.hoo.admin.domain.universe.Universe;
import com.hoo.admin.domain.universe.UniverseTreeComponent;
import com.hoo.admin.domain.universe.piece.Piece;
import com.hoo.admin.domain.universe.space.Space;

import java.util.List;

public record ViewPublicUniverseResult(
        Long universeId,
        Long thumbMusicId,
        Long thumbnailId,
        Long innerImageId,
        Long ownerId,
        Long createdTime,
        Long updatedTime,
        Long view,
        Integer like,
        String title,
        String description,
        String owner,
        CategoryInfo category,
        Boolean isMine,
        Boolean isLiked,
        List<String> hashtags,
        List<SpaceTreeInfo> spaces,
        List<PieceTreeInfo> pieces
) {

    public static ViewPublicUniverseResult of(TreeInfo root, Long userId, boolean isLiked) {
        if (root.getUniverseTreeComponent() instanceof Universe universe)
            return new ViewPublicUniverseResult(
                    universe.getId(),
                    universe.getFileInfo().getThumbMusicId(),
                    universe.getFileInfo().getThumbnailId(),
                    universe.getFileInfo().getImageId(),
                    universe.getAuthorInfo().getId(),
                    universe.getDateInfo().getCreatedTime().toEpochSecond(),
                    universe.getDateInfo().getUpdatedTime().toEpochSecond(),
                    universe.getSocialInfo().getViewCount(),
                    universe.getSocialInfo().getLikeCount(),
                    universe.getBasicInfo().getTitle(),
                    universe.getBasicInfo().getDescription(),
                    universe.getAuthorInfo().getNickname(),
                    CategoryInfo.of(universe.getCategory()),
                    universe.isMine(userId),
                    isLiked,
                    universe.getSocialInfo().getHashtags(),
                    root.getChildren().stream()
                            .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Space)
                            .map(treeInfo -> SpaceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                            .toList(),
                    root.getChildren().stream()
                            .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Piece)
                            .map(treeInfo -> PieceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                            .toList()
            );

        throw new AarException(AarErrorCode.LOAD_ENTITY_FAILED);
    }

    public record SpaceTreeInfo(
            Long spaceId,
            Long parentSpaceId,
            Long innerImageId,
            Integer depth,
            String title,
            String description,
            Float startX,
            Float startY,
            Float endX,
            Float endY,
            Long createdTime,
            Long updatedTime,
            List<TraversalUniverseResult.SpaceTreeInfo> spaces,
            List<TraversalUniverseResult.PieceTreeInfo> pieces
    ) {

        public static SpaceTreeInfo of(UniverseTreeComponent component) {
            if (component instanceof Space space)
                return new SpaceTreeInfo(
                        space.getId(),
                        space.getBasicInfo().getParentSpaceId() == null ? -1 : space.getBasicInfo().getParentSpaceId(),
                        space.getFileInfo().getImageId(),
                        space.getTreeInfo().getDepth(),
                        space.getBasicInfo().getTitle(),
                        space.getBasicInfo().getDescription(),
                        space.getPosInfo().getSx(),
                        space.getPosInfo().getSy(),
                        space.getPosInfo().getEx(),
                        space.getPosInfo().getEy(),
                        space.getDateInfo().getCreatedTime().toEpochSecond(),
                        space.getDateInfo().getUpdatedTime().toEpochSecond(),
                        space.getTreeInfo().getChildren().stream()
                                .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Space)
                                .map(treeInfo -> TraversalUniverseResult.SpaceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                                .toList(),
                        space.getTreeInfo().getChildren().stream()
                                .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Piece)
                                .map(treeInfo -> TraversalUniverseResult.PieceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                                .toList()
                );

            else return null;
        }
    }

    public record PieceTreeInfo(
            Long pieceId,
            Long parentSpaceId,
            Long innerImageId,
            Integer depth,
            String title,
            String description,
            Float startX,
            Float startY,
            Float endX,
            Float endY,
            Long createdTime,
            Long updatedTime
    ) {

        public static PieceTreeInfo of(UniverseTreeComponent component) {
            if (component instanceof Piece piece)
                return new PieceTreeInfo(
                        piece.getId(),
                        piece.getBasicInfo().getParentSpaceId() == null ? -1 : piece.getBasicInfo().getParentSpaceId(),
                        piece.getFileInfo().getImageId(),
                        piece.getTreeInfo().getDepth(),
                        piece.getBasicInfo().getTitle(),
                        piece.getBasicInfo().getDescription(),
                        piece.getPosInfo().getSx(),
                        piece.getPosInfo().getSy(),
                        piece.getPosInfo().getEx(),
                        piece.getPosInfo().getEy(),
                        piece.getDateInfo().getCreatedTime().toEpochSecond(),
                        piece.getDateInfo().getUpdatedTime().toEpochSecond()
                );

            else return null;
        }
    }
}
