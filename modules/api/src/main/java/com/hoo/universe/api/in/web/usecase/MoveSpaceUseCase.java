package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.MoveSpaceWithTwoPointCommand;
import com.hoo.universe.api.in.web.dto.result.MoveSpaceWithTwoPointResult;

import java.util.UUID;

public interface MoveSpaceUseCase {
    MoveSpaceWithTwoPointResult moveSpaceWithTwoPoint(UUID universeID, UUID spaceID, MoveSpaceWithTwoPointCommand command);
}
