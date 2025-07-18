package com.hoo.universe.api.in;

import com.hoo.universe.api.dto.command.CreateUniverseCommand;
import com.hoo.universe.api.dto.result.CreateUniverseResult;

public interface CreateUniverseUseCase {
    CreateUniverseResult createNewUniverse(CreateUniverseCommand command);
}
