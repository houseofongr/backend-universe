package com.hoo.universe.api.in.sound;

import com.hoo.universe.api.dto.command.sound.CreateSoundCommand;
import com.hoo.universe.api.dto.result.sound.CreateSoundResult;

import java.util.UUID;

public interface CreateSoundUseCase {
    CreateSoundResult createNewSound(UUID universeID, UUID pieceID, CreateSoundCommand command);
}
