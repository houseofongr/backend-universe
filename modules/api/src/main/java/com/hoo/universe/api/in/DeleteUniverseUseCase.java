package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.DeleteUniverseResult;

import java.util.UUID;

public interface DeleteUniverseUseCase {
    DeleteUniverseResult deleteUniverse(UUID universeID);
}
