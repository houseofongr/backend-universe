package com.hoo.universe.api.dto.result.space;

import java.net.URI;

public record OverwriteSpaceFileResult(
        URI newBackgroundFileUrl
) {
}
