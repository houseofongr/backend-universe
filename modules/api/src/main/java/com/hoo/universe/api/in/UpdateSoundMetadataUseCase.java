package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.UpdateSoundMetadataCommand;
import com.hoo.universe.api.in.dto.UpdateSoundMetadataResult;

import java.util.UUID;

public interface UpdateSoundMetadataUseCase {
    UpdateSoundMetadataResult updateSoundMetadata(UUID universeID, UUID pieceID, UUID soundID, UpdateSoundMetadataCommand command);
}
