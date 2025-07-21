package com.hoo.universe.api.in;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.universe.api.in.dto.OverwriteSoundFileResult;

import java.util.UUID;

public interface OverwriteSoundFileUseCase {
    OverwriteSoundFileResult overwriteSoundAudio(UUID universeID, UUID pieceID, UUID soundID, FileCommand audio);
}
