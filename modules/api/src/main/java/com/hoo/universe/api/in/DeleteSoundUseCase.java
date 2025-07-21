package com.hoo.universe.api.in;

import com.hoo.universe.api.in.dto.DeleteSoundResult;

import java.util.UUID;

public interface DeleteSoundUseCase {
    DeleteSoundResult deleteSound(UUID universeID, UUID pieceID, UUID soundID);
}
