package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.UpdateSoundMetadataCommand;
import com.hoo.universe.api.in.web.dto.result.UpdateSoundMetadataResult;

import java.util.UUID;

public interface UpdateSoundMetadataUseCase {
    UpdateSoundMetadataResult updateSoundMetadata(UUID universeID, UUID pieceID, UUID soundID, UpdateSoundMetadataCommand command);
}
