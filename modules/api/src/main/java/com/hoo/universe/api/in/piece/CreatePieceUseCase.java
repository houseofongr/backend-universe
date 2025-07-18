package com.hoo.universe.api.in.piece;

import com.hoo.universe.api.dto.command.piece.CreatePieceWithTwoPointCommand;
import com.hoo.universe.api.dto.result.piece.CreatePieceResult;

import java.util.UUID;

public interface CreatePieceUseCase {
    CreatePieceResult createNewPieceWithTwoPoint(UUID universeID, CreatePieceWithTwoPointCommand command);
}
