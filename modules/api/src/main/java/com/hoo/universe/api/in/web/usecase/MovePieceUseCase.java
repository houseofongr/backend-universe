package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.MovePieceWithTwoPointCommand;
import com.hoo.universe.api.in.web.dto.result.MovePieceWithTwoPointResult;

import java.util.UUID;

public interface MovePieceUseCase {
    MovePieceWithTwoPointResult movePieceWithTwoPoint(UUID universeID, UUID pieceID, MovePieceWithTwoPointCommand command);
}
