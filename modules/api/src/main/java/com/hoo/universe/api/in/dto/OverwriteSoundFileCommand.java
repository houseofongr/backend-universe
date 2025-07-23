package com.hoo.universe.api.in.dto;


import com.hoo.common.internal.api.file.dto.UploadFileCommand;

import java.util.UUID;

public record OverwriteSoundFileCommand(
        UUID soundID, UploadFileCommand.FileSource audioFileCommand
) {
}
