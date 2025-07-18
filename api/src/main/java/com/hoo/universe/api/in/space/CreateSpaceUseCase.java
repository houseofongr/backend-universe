package com.hoo.universe.api.in.space;

import com.hoo.universe.api.dto.command.space.CreateSpaceWithTwoPointCommand;
import com.hoo.universe.api.dto.result.space.CreateSpaceResult;

import java.util.UUID;

public interface CreateSpaceUseCase {
    CreateSpaceResult createSpaceWithTwoPoint(UUID universeID, UUID parentSpaceID, CreateSpaceWithTwoPointCommand command);
}
