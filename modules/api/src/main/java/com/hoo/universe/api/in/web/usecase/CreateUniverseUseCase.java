package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.CreateUniverseCommand;
import com.hoo.universe.api.in.web.dto.result.CreateUniverseResult;

public interface CreateUniverseUseCase {
    CreateUniverseResult createNewUniverse(CreateUniverseCommand command);
}
