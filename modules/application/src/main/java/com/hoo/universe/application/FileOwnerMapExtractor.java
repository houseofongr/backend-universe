package com.hoo.universe.application;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.out.dto.OpenPieceQueryResult;
import com.hoo.universe.api.out.dto.UniverseListQueryInfo;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FileOwnerMapExtractor {

    public Map<UUID, UUID> extractFileOwnerMap(Universe universe) {

        Map<UUID, UUID> fileOwnerMap = new HashMap<>();

        UUID ownerID = universe.getOwner().getId();
        fileOwnerMap.put(universe.getUniverseMetadata().getThumbmusicID(), ownerID);
        fileOwnerMap.put(universe.getUniverseMetadata().getThumbnailID(), ownerID);
        fileOwnerMap.put(universe.getUniverseMetadata().getBackgroundID(), ownerID);

        for (var s : universe.getSpaces()) collectSpaceFileIds(s, ownerID, fileOwnerMap);
        for (var p : universe.getPieces()) collectPieceFileIds(p, ownerID, fileOwnerMap);

        return fileOwnerMap;
    }

    public Map<UUID, UUID> extractFileOwnerMap(PageQueryResult<UniverseListQueryInfo> queryResult) {
        Map<UUID, UUID> fileOwnerMap = new HashMap<>();
        for (UniverseListQueryInfo universeListQueryInfo : queryResult.content())
            fileOwnerMap.putAll(universeListQueryInfo.extractFileOwnerMap());
        return fileOwnerMap;
    }

    public Map<UUID, UUID> extractFileOwnerMap(OpenPieceQueryResult queryResult) {
        Map<UUID, UUID> fileOwnerMap = new HashMap<>();

        for (OpenPieceQueryResult.SoundInfo soundInfo : queryResult.sounds().content())
            fileOwnerMap.put(soundInfo.audioFileID(), queryResult.ownerID());

        return fileOwnerMap;
    }

    private void collectSpaceFileIds(Space space, UUID ownerID, Map<UUID, UUID> fileOwnerMap) {

        fileOwnerMap.put(space.getSpaceMetadata().getBackgroundID(), ownerID);

        for (var s : space.getSpaces()) collectSpaceFileIds(s, ownerID, fileOwnerMap);
        for (var p : space.getPieces()) collectPieceFileIds(p, ownerID, fileOwnerMap);
    }

    private void collectPieceFileIds(Piece piece, UUID ownerID, Map<UUID, UUID> fileOwnerMap) {

        fileOwnerMap.put(piece.getPieceMetadata().getImageID(), ownerID);
    }
}
