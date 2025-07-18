package com.hoo.common.internal.api.dto;

import com.hoo.common.enums.Authority;

import java.util.UUID;

public record UploadFileResponse(
        UUID id,
        UUID ownerID,
        Long size,
        String realName,
        String fileSystemName,
        com.hoo.common.enums.FileType fileType,
        Authority authority
) {
}
