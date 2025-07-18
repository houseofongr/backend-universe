package com.hoo.universe.api.in.sound;

import com.hoo.universe.api.dto.result.sound.DeleteSoundResult;

import java.util.UUID;

public interface DeleteSoundUseCase {
    DeleteSoundResult deleteSound(UUID universeID, UUID pieceID, UUID soundID);
}
