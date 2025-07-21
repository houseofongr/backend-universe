package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.MovePieceWithTwoPointCommand;
import com.hoo.universe.api.in.dto.MovePieceWithTwoPointResult;

import java.util.UUID;

public interface MovePieceUseCase {
    MovePieceWithTwoPointResult movePieceWithTwoPoint(UUID universeID, UUID pieceID, MovePieceWithTwoPointCommand command);
}
