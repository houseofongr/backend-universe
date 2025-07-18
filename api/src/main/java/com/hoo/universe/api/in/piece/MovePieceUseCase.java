package com.hoo.universe.api.in.piece;

import com.hoo.universe.api.dto.command.piece.MovePieceWithTwoPointCommand;
import com.hoo.universe.api.dto.result.piece.MovePieceWithTwoPointResult;

import java.util.UUID;

public interface MovePieceUseCase {
    MovePieceWithTwoPointResult movePieceWithTwoPoint(UUID universeID, UUID pieceID, MovePieceWithTwoPointCommand command);
}
