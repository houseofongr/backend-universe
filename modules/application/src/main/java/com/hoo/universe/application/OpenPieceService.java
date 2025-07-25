package com.hoo.universe.application;

import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.in.OpenPieceUseCase;
import com.hoo.universe.api.in.dto.OpenPieceResult;
import com.hoo.universe.api.out.FileUrlResolveInCase;
import com.hoo.universe.api.out.QueryUniversePort;
import com.hoo.universe.api.out.dto.OpenPieceQueryResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenPieceService implements OpenPieceUseCase {

    private final QueryUniversePort queryUniversePort;
    private final FileUrlResolveInCase fileUrlResolveInCase;
    private final FileOwnerMapExtractor fileOwnerMapExtractor;
    private final ApplicationMapper applicationMapper;

    @Override
    public OpenPieceResult openPieceWithSounds(UUID pieceID, PageRequest pageRequest) {
        OpenPieceQueryResult queryResult = queryUniversePort.searchPiece(pieceID, pageRequest);
        Map<UUID, UUID> fileOwnerMap = fileOwnerMapExtractor.extractFileOwnerMap(queryResult);
        Map<UUID, URI> fileUrlMap = fileUrlResolveInCase.resolveBatch(fileOwnerMap);
        return applicationMapper.mapToOpenPieceResult(queryResult, fileUrlMap);
    }
}
