package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.result.DeleteUniverseResult;

import java.util.UUID;

public interface DeleteUniverseUseCase {
    DeleteUniverseResult deleteUniverse(UUID universeID);
}
