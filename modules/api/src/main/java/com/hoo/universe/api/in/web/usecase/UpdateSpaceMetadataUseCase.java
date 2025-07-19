package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.UpdateSpaceMetadataCommand;
import com.hoo.universe.api.in.web.dto.result.UpdateSpaceMetadataResult;

import java.util.UUID;

public interface UpdateSpaceMetadataUseCase {
    UpdateSpaceMetadataResult updateSpaceMetadata(UUID universeID, UUID spaceID, UpdateSpaceMetadataCommand command);
}
