package com.hoo.universe.api.in.sound;

import com.hoo.universe.api.dto.command.sound.UpdateSoundMetadataCommand;
import com.hoo.universe.api.dto.result.sound.UpdateSoundMetadataResult;

import java.util.UUID;

public interface UpdateSoundMetadataUseCase {
    UpdateSoundMetadataResult updateSoundMetadata(UUID universeID, UUID pieceID, UUID soundID, UpdateSoundMetadataCommand command);
}
