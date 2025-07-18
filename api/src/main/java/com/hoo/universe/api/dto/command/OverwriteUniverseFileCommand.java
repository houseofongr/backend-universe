package com.hoo.universe.api.dto.command;

import com.hoo.common.internal.api.dto.UploadFileRequest;

public record OverwriteUniverseFileCommand(
        UploadFileRequest thumbmusic,
        UploadFileRequest thumbnail,
        UploadFileRequest background
) {
}
