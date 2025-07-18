package com.hoo.universe.api.in.piece;

import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.dto.result.piece.OpenPieceResult;

import java.util.UUID;

public interface OpenPieceUseCase {
    OpenPieceResult openPieceWithSounds(UUID pieceID, PageRequest pageRequest);
}
