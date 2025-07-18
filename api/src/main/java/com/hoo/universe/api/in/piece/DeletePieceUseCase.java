package com.hoo.universe.api.in.piece;

import com.hoo.universe.api.dto.result.piece.DeletePieceResult;

import java.util.UUID;

public interface DeletePieceUseCase {
    DeletePieceResult deletePiece(UUID universeID, UUID pieceID);
}
