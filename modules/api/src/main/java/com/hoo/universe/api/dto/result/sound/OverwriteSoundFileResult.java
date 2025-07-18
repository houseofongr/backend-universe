package com.hoo.universe.api.dto.result.sound;

import java.util.UUID;

public record OverwriteSoundFileResult(
        UUID deletedAudioID,
        UUID newAudioID) {
}
