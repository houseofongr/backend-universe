package com.hoo.admin.application.port.in.sound;

public record CreateSoundResult(
        String message,
        Long soundId,
        Long pieceId,
        Long audioFileId,
        String title,
        String description,
        Boolean hidden
) {
}
