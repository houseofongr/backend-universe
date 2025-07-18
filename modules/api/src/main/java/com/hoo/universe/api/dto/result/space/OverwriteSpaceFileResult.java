package com.hoo.universe.api.dto.result.space;

import java.util.UUID;

public record OverwriteSpaceFileResult(
        UUID deletedBackgroundID,
        UUID newBackgroundID
) {
}
