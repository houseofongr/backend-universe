package com.hoo.universe.api.in.dto;

import java.net.URI;
import java.util.UUID;

public record CreateSoundResult(
        UUID soundID,
        URI audioFileUrl,
        String title,
        String description,
        Boolean hidden,
        Long createdTime
) {
}
