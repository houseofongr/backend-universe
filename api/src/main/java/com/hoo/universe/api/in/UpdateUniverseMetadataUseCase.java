package com.hoo.universe.api.in;

import com.hoo.universe.api.dto.command.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.dto.result.UpdateUniverseMetadataResult;

import java.util.UUID;

public interface UpdateUniverseMetadataUseCase {
    UpdateUniverseMetadataResult updateUniverseMetadata(UUID universeID, UpdateUniverseMetadataCommand command);
}
