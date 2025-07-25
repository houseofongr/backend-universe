package com.hoo.universe.application;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.universe.api.in.dto.OpenPieceResult;
import com.hoo.universe.api.in.dto.OpenUniverseResult;
import com.hoo.universe.api.in.dto.UniverseListInfo;
import com.hoo.universe.api.out.dto.OpenPieceQueryResult;
import com.hoo.universe.api.out.dto.UniverseListQueryInfo;
import com.hoo.universe.domain.Piece;
import com.hoo.universe.domain.Space;
import com.hoo.universe.domain.Universe;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ApplicationMapper {

    private static OpenUniverseResult.SpaceInfo mapToSpaceInfo(Universe universe, Space space, Map<UUID, URI> uriMap) {
        return new OpenUniverseResult.SpaceInfo(
                space.getId().uuid(),
                space.isFirstNode() ? null : space.getParentSpaceID().uuid(),
                uriMap.get(space.getSpaceMetadata().getBackgroundID()),
                universe.getDepth(space.getId()),
                space.getCommonMetadata().getTitle(),
                space.getCommonMetadata().getDescription(),
                space.getSpaceMetadata().isHidden(),
                space.getOutline().getPoints(),
                space.getCommonMetadata().getCreatedTime().toEpochSecond(),
                space.getCommonMetadata().getUpdatedTime().toEpochSecond(),
                space.getSpaces().stream().map(s -> mapToSpaceInfo(universe, s, uriMap)).toList(),
                space.getPieces().stream().map(p -> mapToPieceInfo(universe, p, uriMap)).toList()
        );
    }

    private static OpenUniverseResult.PieceInfo mapToPieceInfo(Universe universe, Piece piece, Map<UUID, URI> uriMap) {
        return new OpenUniverseResult.PieceInfo(
                piece.getId().uuid(),
                piece.isFirstNode() ? null : piece.getParentSpaceID().uuid(),
                uriMap.get(piece.getPieceMetadata().getImageID()),
                universe.getDepth(piece.getId()),
                piece.getCommonMetadata().getTitle(),
                piece.getCommonMetadata().getDescription(),
                piece.getPieceMetadata().isHidden(),
                piece.getOutline().getPoints(),
                piece.getCommonMetadata().getCreatedTime().toEpochSecond(),
                piece.getCommonMetadata().getUpdatedTime().toEpochSecond()
        );
    }

    public OpenUniverseResult mapToOpenUniverseResult(Universe universe, Map<UUID, URI> uriMap) {
        return new OpenUniverseResult(
                universe.getId().uuid(),
                uriMap.get(universe.getUniverseMetadata().getThumbmusicID()),
                uriMap.get(universe.getUniverseMetadata().getThumbnailID()),
                uriMap.get(universe.getUniverseMetadata().getBackgroundID()),
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
                universe.getSpaces().stream().map(s -> mapToSpaceInfo(universe, s, uriMap)).toList(),
                universe.getPieces().stream().map(p -> mapToPieceInfo(universe, p, uriMap)).toList()
        );
    }

    public OpenPieceResult mapToOpenPieceResult(OpenPieceQueryResult queryResult, Map<UUID, URI> uriMap) {
        return new OpenPieceResult(
                queryResult.pieceID(),
                queryResult.title(),
                queryResult.description(),
                queryResult.hidden(),
                queryResult.createdTime(),
                queryResult.updatedTime(),
                mapToSoundInfo(queryResult.sounds(), uriMap)
        );
    }

    private PageQueryResult<OpenPieceResult.SoundInfo> mapToSoundInfo(PageQueryResult<OpenPieceQueryResult.SoundInfo> sounds, Map<UUID, URI> uriMap) {
        return sounds.map(soundInfo -> new OpenPieceResult.SoundInfo(
                soundInfo.soundID(),
                uriMap.get(soundInfo.audioFileID()),
                soundInfo.title(),
                soundInfo.description(),
                soundInfo.hidden(),
                soundInfo.createdTime(),
                soundInfo.updatedTime()
        ));
    }

    public PageQueryResult<UniverseListInfo> mapToUniverseListInfoPageQueryResult(PageQueryResult<UniverseListQueryInfo> queryResult, Map<UUID, URI> uriMap) {
        return queryResult.map(universeListQueryInfo -> mapToUniverseListInfoPageQueryResult(universeListQueryInfo, uriMap));
    }

    private UniverseListInfo mapToUniverseListInfoPageQueryResult(UniverseListQueryInfo universeListQueryInfo, Map<UUID, URI> uriMap) {
        return new UniverseListInfo(
                universeListQueryInfo.id(),
                uriMap.get(universeListQueryInfo.thumbmusicFileID()),
                uriMap.get(universeListQueryInfo.thumbnailFileID()),
                universeListQueryInfo.ownerID(),
                universeListQueryInfo.createdTime(),
                universeListQueryInfo.updatedTime(),
                universeListQueryInfo.view(),
                universeListQueryInfo.like(),
                universeListQueryInfo.title(),
                universeListQueryInfo.description(),
                universeListQueryInfo.owner(),
                universeListQueryInfo.accessLevel(),
                universeListQueryInfo.category(),
                universeListQueryInfo.hashtags()
        );
    }
}
