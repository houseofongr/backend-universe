package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.CreateSoundCommand;
import com.hoo.universe.api.in.dto.CreateSoundResult;

import java.util.UUID;

public interface CreateSoundUseCase {
    CreateSoundResult createNewSound(UUID universeID, UUID pieceID, CreateSoundCommand command);
}
