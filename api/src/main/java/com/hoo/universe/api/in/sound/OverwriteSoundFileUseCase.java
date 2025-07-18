package com.hoo.universe.api.in.sound;

import com.hoo.common.internal.api.dto.FileCommand;
import com.hoo.universe.api.dto.result.sound.OverwriteSoundFileResult;

import java.util.UUID;

public interface OverwriteSoundFileUseCase {
    OverwriteSoundFileResult overwriteSoundAudio(UUID universeID, UUID pieceID, UUID soundID, FileCommand audio);
}
