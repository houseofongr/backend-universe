package com.hoo.universe.api.dto.result.sound;

import java.util.UUID;

public record DeleteSoundResult(
        UUID deletedSoundID,
        UUID deletedAudioID
) {
}
