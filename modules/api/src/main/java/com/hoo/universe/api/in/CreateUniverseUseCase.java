package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.CreateUniverseCommand;
import com.hoo.universe.api.in.dto.CreateUniverseResult;

public interface CreateUniverseUseCase {
    CreateUniverseResult createNewUniverse(CreateUniverseCommand command);
}
