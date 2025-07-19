package com.hoo.universe.api.in.web.usecase;

import com.hoo.universe.api.in.web.dto.result.DeleteSoundResult;

import java.util.UUID;

public interface DeleteSoundUseCase {
    DeleteSoundResult deleteSound(UUID universeID, UUID pieceID, UUID soundID);
}
