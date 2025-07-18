package com.hoo.common.internal.message.dto;

import java.util.UUID;

public record FileDeleteMessage(
        UUID deleteFileID,
        Long deleteTimestamp
) {
}
