package com.hoo.universe.api.in.space;

import com.hoo.universe.api.dto.command.space.MoveSpaceWithTwoPointCommand;
import com.hoo.universe.api.dto.result.space.MoveSpaceWithTwoPointResult;

import java.util.UUID;

public interface MoveSpaceUseCase {
    MoveSpaceWithTwoPointResult moveSpaceWithTwoPoint(UUID universeID, UUID spaceID, MoveSpaceWithTwoPointCommand command);
}
