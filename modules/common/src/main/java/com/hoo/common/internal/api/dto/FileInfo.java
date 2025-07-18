package com.hoo.common.internal.api.dto;

import java.net.URI;
import java.util.UUID;

public record FileInfo(
        UUID id,
        URI fileUrl,
        FileMetadata metadata
) {
}
