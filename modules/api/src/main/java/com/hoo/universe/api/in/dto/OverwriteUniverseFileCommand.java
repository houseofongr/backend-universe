package com.hoo.universe.api.in.dto;


import com.hoo.common.internal.api.file.dto.UploadFileCommand;

public record OverwriteUniverseFileCommand(
        UploadFileCommand.FileSource thumbmusic,
        UploadFileCommand.FileSource thumbnail,
        UploadFileCommand.FileSource background
) {
}
