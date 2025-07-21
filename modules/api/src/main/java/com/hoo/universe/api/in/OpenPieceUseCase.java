package com.hoo.universe.api.in;

import com.hoo.common.web.dto.PageRequest;
import com.hoo.universe.api.in.dto.OpenPieceResult;

import java.util.UUID;

public interface OpenPieceUseCase {
    OpenPieceResult openPieceWithSounds(UUID pieceID, PageRequest pageRequest);
}
