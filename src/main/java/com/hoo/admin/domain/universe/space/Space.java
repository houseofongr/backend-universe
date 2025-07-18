package com.hoo.admin.domain.universe.space;

import com.hoo.admin.domain.universe.*;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
public class Space extends UniverseTreeComponent {
    private final DateInfo dateInfo;
    private final ImageFileInfo fileInfo;
    private final SpacePieceBasicInfo basicInfo;
    private final PosInfo posInfo;

    private Space(Long id, ImageFileInfo fileInfo, SpacePieceBasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
    }

    public static Space create(Long id, Long innerImageId, Long universeId, Long parentSpaceId, String title, String description, Float sx, Float sy, Float ex, Float ey, Boolean hidden) {

        return new Space(
                id,
                new ImageFileInfo(innerImageId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description, hidden),
                null,
                new PosInfo(sx, sy, ex, ey),
                null
        );
    }

    public static Space loadTreeComponent(Long id, Long innerImageFileId, Long universeId, Long parentSpaceId, String title, String description, Float sx, Float sy, Float ex, Float ey, Boolean hidden, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Space(
                id,
                new ImageFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description, hidden),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(sx, sy, ex, ey),
                null
        );
    }

    public static Space loadWithoutRelation(Long id, Long innerImageFileId, String title, String description,Float sx, Float sy, Float ex, Float ey,  Boolean hidden, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Space(
                id,
                new ImageFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(null, null, title, description, hidden),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(sx, sy, ex, ey),
                null
        );
    }

    public boolean isRoot() {
        return this.basicInfo.getParentSpaceId() == null || this.basicInfo.getParentSpaceId() == -1;
    }

}
