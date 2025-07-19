package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.result.OpenUniverseResult;

import java.util.UUID;

public interface OpenUniverseUseCase {
    OpenUniverseResult openUniverseWithComponents(UUID universeID);
}
