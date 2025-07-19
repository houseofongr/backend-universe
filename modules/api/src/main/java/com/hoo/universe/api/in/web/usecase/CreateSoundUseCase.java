package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.command.CreateSoundCommand;
import com.hoo.universe.api.in.web.dto.result.CreateSoundResult;

import java.util.UUID;

public interface CreateSoundUseCase {
    CreateSoundResult createNewSound(UUID universeID, UUID pieceID, CreateSoundCommand command);
}
