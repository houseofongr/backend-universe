package com.hoo.admin.application.port.in.universe;

import com.hoo.admin.application.service.AdminErrorCode;
import com.hoo.admin.application.service.AdminException;
import com.hoo.admin.domain.universe.Universe;
import com.hoo.admin.domain.universe.UniverseTreeComponent;
import com.hoo.admin.domain.universe.space.Space;
import com.hoo.admin.domain.universe.TreeInfo;
import com.hoo.admin.domain.universe.piece.Piece;

import java.util.List;

public record TraversalUniverseResult(
        Long universeId,
        Long innerImageId,
        List<SpaceTreeInfo> spaces,
        List<PieceTreeInfo> pieces
) {

    public static TraversalUniverseResult of(TreeInfo root) {
        if (root.getUniverseTreeComponent() instanceof Universe universe)
            return new TraversalUniverseResult(
                    universe.getId(),
                    universe.getFileInfo().getImageId(),
                    root.getChildren().stream()
                            .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Space)
                            .map(treeInfo -> SpaceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                            .toList(),
                    root.getChildren().stream()
                            .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Piece)
                            .map(treeInfo -> PieceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                            .toList()
            );

        throw new AdminException(AdminErrorCode.LOAD_ENTITY_FAILED);
    }

    public record SpaceTreeInfo(
            Long spaceId,
            Long parentSpaceId,
            Long innerImageId,
            Integer depth,
            String title,
            String description,
            Boolean hidden,
            Float startX,
            Float startY,
            Float endX,
            Float endY,
            Long createdTime,
            Long updatedTime,
            List<SpaceTreeInfo> spaces,
            List<PieceTreeInfo> pieces
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
                        space.getBasicInfo().getHidden(),
                        space.getPosInfo().getSx(),
                        space.getPosInfo().getSy(),
                        space.getPosInfo().getEx(),
                        space.getPosInfo().getEy(),
                        space.getDateInfo().getCreatedTime().toEpochSecond(),
                        space.getDateInfo().getUpdatedTime().toEpochSecond(),
                        space.getTreeInfo().getChildren().stream()
                                .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Space)
                                .map(treeInfo -> SpaceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
                                .toList(),
                        space.getTreeInfo().getChildren().stream()
                                .filter(treeInfo -> treeInfo.getUniverseTreeComponent() instanceof Piece)
                                .map(treeInfo -> PieceTreeInfo.of(treeInfo.getUniverseTreeComponent()))
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
            Boolean hidden,
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
                        piece.getBasicInfo().getHidden(),
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
