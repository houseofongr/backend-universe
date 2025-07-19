package com.hoo.universe.api.in.web.usecase;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.universe.api.in.web.dto.result.OverwriteSoundFileResult;

import java.util.UUID;

public interface OverwriteSoundFileUseCase {
    OverwriteSoundFileResult overwriteSoundAudio(UUID universeID, UUID pieceID, UUID soundID, FileCommand audio);
}
