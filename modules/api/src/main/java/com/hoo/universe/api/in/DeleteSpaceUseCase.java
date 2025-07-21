package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.DeleteSpaceResult;

import java.util.UUID;

public interface DeleteSpaceUseCase {
    DeleteSpaceResult deleteSpace(UUID universeID, UUID spaceID);
}
