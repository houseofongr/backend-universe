package com.hoo.universe.api.in;

import com.hoo.universe.api.dto.result.OpenUniverseResult;

import java.util.UUID;

public interface OpenUniverseUseCase {
    OpenUniverseResult openUniverseWithComponents(UUID universeID);
}
