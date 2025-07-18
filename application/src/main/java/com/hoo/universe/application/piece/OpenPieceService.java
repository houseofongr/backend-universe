package com.hoo.universe.application.piece;

import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.dto.result.piece.OpenPieceResult;
import com.hoo.universe.api.in.piece.OpenPieceUseCase;
import com.hoo.universe.api.out.persistence.QueryUniversePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OpenPieceService implements OpenPieceUseCase {

    private final QueryUniversePort queryUniversePort;

    @Override
    public OpenPieceResult openPieceWithSounds(UUID pieceID, PageRequest pageRequest) {
        return queryUniversePort.searchPiece(pieceID, pageRequest);
    }
}
