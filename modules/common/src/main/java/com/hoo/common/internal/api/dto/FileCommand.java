package com.hoo.common.internal.api.dto;

import java.io.InputStream;

public record FileCommand(
        InputStream inputStream,
        Long size,
        String name,
        String contentType
) {
}
