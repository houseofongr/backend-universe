package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.CreateSpaceWithTwoPointCommand;
import com.hoo.universe.api.in.dto.CreateSpaceResult;

import java.util.UUID;

public interface CreateSpaceUseCase {
    CreateSpaceResult createSpaceWithTwoPoint(UUID universeID, UUID parentSpaceID, CreateSpaceWithTwoPointCommand command);
}
