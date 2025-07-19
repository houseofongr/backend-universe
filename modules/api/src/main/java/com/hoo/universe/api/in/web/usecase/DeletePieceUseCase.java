package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.result.DeletePieceResult;

import java.util.UUID;

public interface DeletePieceUseCase {
    DeletePieceResult deletePiece(UUID universeID, UUID pieceID);
}
