package com.hoo.universe.application;

import com.hoo.universe.api.in.dto.OpenUniverseResult;
import com.hoo.universe.api.in.OpenUniverseUseCase;
import com.hoo.universe.api.out.LoadUniversePort;
import com.hoo.universe.api.out.dto.OpenUniverseQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static com.hoo.common.util.OptionalUtil.getOrDefault;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenUniverseService implements OpenUniverseUseCase {

    private final LoadUniversePort loadUniversePort;
    private final FileUrlResolver fileUrlResolver;

    @Override
    public OpenUniverseResult openUniverseWithComponents(UUID universeID) {

        OpenUniverseQueryResult queryResult = OpenUniverseQueryResult.from(loadUniversePort.loadUniverseExceptSounds(universeID));

        return mapToAPIResult(queryResult);
    }

    private OpenUniverseResult mapToAPIResult(OpenUniverseQueryResult queryResult) {

        Map<UUID, UUID> fileOwnerMap = queryResult.extractFileOwnerMap();
        Map<UUID, URI> uriMap = fileUrlResolver.resolveBatch(fileOwnerMap);

        return mapToAPIResult(queryResult, uriMap);
    }

    private OpenUniverseResult mapToAPIResult(OpenUniverseQueryResult queryResult, Map<UUID, URI> uriMap) {

        return new OpenUniverseResult(
                queryResult.id(),
                uriMap.get(queryResult.thumbmusicFileID()),
                uriMap.get(queryResult.thumbnailFileID()),
                uriMap.get(queryResult.backgroundFileID()),
                queryResult.ownerID(),
                queryResult.createdTime(),
                queryResult.updatedTime(),
                queryResult.view(),
                queryResult.like(),
                queryResult.title(),
                queryResult.description(),
                queryResult.owner(),
                queryResult.accessLevel(),
                queryResult.category(),
                queryResult.hashtags(),
                queryResult.spaces().stream().map(s -> mapToSpaceInfo(s, uriMap)).toList(),
                queryResult.pieces().stream().map(p -> mapToPieceInfo(p, uriMap)).toList()
        );
    }

    private OpenUniverseResult.SpaceInfo mapToSpaceInfo(OpenUniverseQueryResult.SpaceInfo space, Map<UUID, URI> uriMap) {

        return new OpenUniverseResult.SpaceInfo(
                space.spaceID(),
                space.parentSpaceID(),
                uriMap.get(space.backgroundFileID()),
                space.depth(),
                space.title(),
                space.description(),
                space.hidden(),
                space.points(),
                space.createdTime(),
                space.updatedTime(),
                space.spaces().stream().map(s -> mapToSpaceInfo(s, uriMap)).toList(),
                space.pieces().stream().map(p -> mapToPieceInfo(p, uriMap)).toList()
        );
    }

    private OpenUniverseResult.PieceInfo mapToPieceInfo(OpenUniverseQueryResult.PieceInfo piece, Map<UUID, URI> uriMap) {

        return new OpenUniverseResult.PieceInfo(
                piece.pieceID(),
                piece.parentSpaceID(),
                getOrDefault(uriMap.get(piece.imageFileID()), null),
                piece.depth(),
                piece.title(),
                piece.description(),
                piece.hidden(),
                piece.points(),
                piece.createdTime(),
                piece.updatedTime()
        );
    }
}
