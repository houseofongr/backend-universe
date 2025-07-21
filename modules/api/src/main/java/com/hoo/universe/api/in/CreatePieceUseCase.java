package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.CreatePieceWithTwoPointCommand;
import com.hoo.universe.api.in.dto.CreatePieceResult;

import java.util.UUID;

public interface CreatePieceUseCase {
    CreatePieceResult createNewPieceWithTwoPoint(UUID universeID, CreatePieceWithTwoPointCommand command);
}
