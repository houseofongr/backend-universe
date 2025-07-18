package com.hoo.admin.domain.universe.piece;

import com.hoo.admin.domain.universe.ImageFileInfo;
import com.hoo.admin.domain.universe.DateInfo;
import com.hoo.admin.domain.universe.UniverseTreeComponent;
import com.hoo.admin.domain.universe.PosInfo;
import com.hoo.admin.domain.universe.SpacePieceBasicInfo;
import com.hoo.admin.domain.universe.TreeInfo;
import com.hoo.admin.domain.universe.piece.sound.Sound;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class Piece extends UniverseTreeComponent {
    private final ImageFileInfo fileInfo;
    private final SpacePieceBasicInfo basicInfo;
    private final DateInfo dateInfo;
    private final PosInfo posInfo;
    private final List<Sound> sounds;

    private Piece(Long id, ImageFileInfo fileInfo, SpacePieceBasicInfo basicInfo, DateInfo dateInfo, PosInfo posInfo, TreeInfo treeInfo, List<Sound> sounds) {
        super(id, treeInfo);
        this.fileInfo = fileInfo;
        this.basicInfo = basicInfo;
        this.dateInfo = dateInfo;
        this.posInfo = posInfo;
        this.sounds = sounds;
    }

    public static Piece create(Long id, Long innerImageId, Long universeId, Long parentSpaceId, String title, String description, Float sx, Float sy, Float ex, Float ey, Boolean hidden) {
        return new Piece(
                id,
                new ImageFileInfo(innerImageId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description, hidden),
                null,
                new PosInfo(sx, sy, ex, ey),
                null,
                null
        );
    }

    public static Piece loadTreeComponent(Long id, Long innerImageFileId, Long universeId, Long parentSpaceId, String title, String description, Float sx, Float sy, Float ex, Float ey, Boolean hidden, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Piece(
                id,
                new ImageFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(universeId, parentSpaceId, title, description, hidden),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(sx, sy, ex, ey),
                null,
                null
        );
    }

    public static Piece loadWithoutRelation(Long id, Long innerImageFileId, String title, String description, Float sx, Float sy, Float ex, Float ey, Boolean hidden, ZonedDateTime createdTime, ZonedDateTime updatedTime) {
        return new Piece(
                id,
                new ImageFileInfo(innerImageFileId),
                new SpacePieceBasicInfo(null, null, title, description, hidden),
                new DateInfo(createdTime, updatedTime),
                new PosInfo(sx, sy, ex, ey),
                null,
                null);
    }

    public static Piece loadWithSound(Long id, Long innerImageFileId, List<Sound> sounds) {
        return new Piece(id,
                new ImageFileInfo(innerImageFileId),
                null,
                null,
                null,
                null,
                sounds
        );
    }

    public boolean isRoot() {
        return this.basicInfo.getParentSpaceId() == null || this.basicInfo.getParentSpaceId() == -1;
    }
}
