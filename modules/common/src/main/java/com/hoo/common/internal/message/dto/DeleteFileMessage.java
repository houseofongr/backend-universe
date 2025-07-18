package com.hoo.common.internal.message.dto;

import java.util.UUID;

public record DeleteFileMessage(
        UUID deleteFileID,
        Long deleteTimestamp
) {
}
