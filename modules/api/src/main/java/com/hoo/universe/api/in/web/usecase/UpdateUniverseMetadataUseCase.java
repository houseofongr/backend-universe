package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.in.web.dto.result.UpdateUniverseMetadataResult;

import java.util.UUID;

public interface UpdateUniverseMetadataUseCase {
    UpdateUniverseMetadataResult updateUniverseMetadata(UUID universeID, UpdateUniverseMetadataCommand command);
}
