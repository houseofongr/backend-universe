package com.hoo.common.internal.api.dto;

import java.net.URI;
import java.util.UUID;

public record UploadFileResult(
        UUID id,
        URI fileUrl,
        Long updatedTimestamp
) {
}
