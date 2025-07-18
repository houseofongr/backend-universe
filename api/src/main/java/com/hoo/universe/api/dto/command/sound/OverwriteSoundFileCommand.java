package com.hoo.universe.api.dto.command.sound;

import com.hoo.common.internal.api.dto.UploadFileRequest;

import java.util.UUID;

public record OverwriteSoundFileCommand(
        UUID soundID, UploadFileRequest audioUploadFileRequest
) {
}
