package com.hoo.universe.api.in.web.dto.result;

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
