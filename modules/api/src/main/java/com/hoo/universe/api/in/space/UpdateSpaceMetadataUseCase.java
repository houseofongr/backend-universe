package com.hoo.universe.api.in.space;

import com.hoo.universe.api.dto.command.space.UpdateSpaceMetadataCommand;
import com.hoo.universe.api.dto.result.space.UpdateSpaceMetadataResult;

import java.util.UUID;

public interface UpdateSpaceMetadataUseCase {
    UpdateSpaceMetadataResult updateSpaceMetadata(UUID universeID, UUID spaceID, UpdateSpaceMetadataCommand command);
}
