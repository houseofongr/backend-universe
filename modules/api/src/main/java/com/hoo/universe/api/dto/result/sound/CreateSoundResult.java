package com.hoo.universe.api.dto.result.sound;

import java.util.UUID;

public record CreateSoundResult(
        UUID soundID,
        UUID audioFileID,
        String title,
        String description,
        Boolean hidden,
        Long createdTime
) {
}
