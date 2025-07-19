package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.CreatePieceWithTwoPointCommand;
import com.hoo.universe.api.in.web.dto.result.CreatePieceResult;

import java.util.UUID;

public interface CreatePieceUseCase {
    CreatePieceResult createNewPieceWithTwoPoint(UUID universeID, CreatePieceWithTwoPointCommand command);
}
