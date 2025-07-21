package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.MoveSpaceWithTwoPointCommand;
import com.hoo.universe.api.in.dto.MoveSpaceWithTwoPointResult;

import java.util.UUID;

public interface MoveSpaceUseCase {
    MoveSpaceWithTwoPointResult moveSpaceWithTwoPoint(UUID universeID, UUID spaceID, MoveSpaceWithTwoPointCommand command);
}
