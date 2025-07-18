package com.hoo.universe.api.in.space;

import com.hoo.universe.api.dto.result.space.DeleteSpaceResult;

import java.util.UUID;

public interface DeleteSpaceUseCase {
    DeleteSpaceResult deleteSpace(UUID universeID, UUID spaceID);
}
