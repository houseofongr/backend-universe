package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.result.DeleteSpaceResult;

import java.util.UUID;

public interface DeleteSpaceUseCase {
    DeleteSpaceResult deleteSpace(UUID universeID, UUID spaceID);
}
