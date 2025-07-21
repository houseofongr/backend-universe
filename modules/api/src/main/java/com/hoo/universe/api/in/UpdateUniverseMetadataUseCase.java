package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.UpdateUniverseMetadataCommand;
import com.hoo.universe.api.in.dto.UpdateUniverseMetadataResult;

import java.util.UUID;

public interface UpdateUniverseMetadataUseCase {
    UpdateUniverseMetadataResult updateUniverseMetadata(UUID universeID, UpdateUniverseMetadataCommand command);
}
