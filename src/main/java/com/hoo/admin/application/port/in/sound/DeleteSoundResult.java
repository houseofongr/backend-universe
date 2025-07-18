package com.hoo.admin.application.port.in.sound;

public record DeleteSoundResult(
        String message,
        Long deletedSoundId,
        Long deletedAudioId
) {
}
