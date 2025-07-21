package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.DeletePieceResult;

import java.util.UUID;

public interface DeletePieceUseCase {
    DeletePieceResult deletePiece(UUID universeID, UUID pieceID);
}
