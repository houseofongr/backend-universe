package com.hoo.common.internal.api.dto;

import com.hoo.common.enums.AccessLevel;

import java.util.UUID;

public record FileMetadata(
        Long size,
        String name,
        String contentType,
        UUID ownerID,
        AccessLevel accessLevel
) {
}
