package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.UpdateSpaceMetadataCommand;
import com.hoo.universe.api.in.dto.UpdateSpaceMetadataResult;

import java.util.UUID;

public interface UpdateSpaceMetadataUseCase {
    UpdateSpaceMetadataResult updateSpaceMetadata(UUID universeID, UUID spaceID, UpdateSpaceMetadataCommand command);
}
