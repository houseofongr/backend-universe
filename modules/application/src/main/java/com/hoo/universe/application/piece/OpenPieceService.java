package com.hoo.universe.application.piece;

import com.hoo.common.web.dto.PageQueryResult;
import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.dto.query.OpenPieceQueryResult;
import com.hoo.universe.api.dto.result.piece.OpenPieceResult;
import com.hoo.universe.api.in.piece.OpenPieceUseCase;
import com.hoo.universe.api.out.persistence.QueryUniversePort;
import com.hoo.universe.application.FileUrlResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenPieceService implements OpenPieceUseCase {

    private final QueryUniversePort queryUniversePort;
    private final FileUrlResolver fileUrlResolver;

    @Override
    public OpenPieceResult openPieceWithSounds(UUID pieceID, PageRequest pageRequest) {

        OpenPieceQueryResult queryResult = queryUniversePort.searchPiece(pieceID, pageRequest);
        Map<UUID, URI> uriMap = fileUrlResolver.resolveBatch(queryResult.extractFileIds());

        return mapToAPIResult(queryResult, uriMap);
    }

    private OpenPieceResult mapToAPIResult(OpenPieceQueryResult queryResult, Map<UUID, URI> uriMap) {

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
}
