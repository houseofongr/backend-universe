package com.hoo.universe.api.in.web.usecase;

import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.in.web.dto.result.OpenPieceResult;

import java.util.UUID;

public interface OpenPieceUseCase {
    OpenPieceResult openPieceWithSounds(UUID pieceID, PageRequest pageRequest);
}
