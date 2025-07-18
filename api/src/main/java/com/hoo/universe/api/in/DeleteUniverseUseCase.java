package com.hoo.universe.api.in;

import com.hoo.universe.api.dto.result.DeleteUniverseResult;

import java.util.UUID;

public interface DeleteUniverseUseCase {
    DeleteUniverseResult deleteUniverse(UUID universeID);
}
