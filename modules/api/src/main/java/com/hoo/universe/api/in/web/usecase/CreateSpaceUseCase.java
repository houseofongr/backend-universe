package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.CreateSpaceWithTwoPointCommand;
import com.hoo.universe.api.in.web.dto.result.CreateSpaceResult;

import java.util.UUID;

public interface CreateSpaceUseCase {
    CreateSpaceResult createSpaceWithTwoPoint(UUID universeID, UUID parentSpaceID, CreateSpaceWithTwoPointCommand command);
}
